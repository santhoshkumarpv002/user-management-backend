package com.example.user_management_backend.service.Impl;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user_management_backend.controller.JwtAuthenticationController;
import com.example.user_management_backend.entity.Otp;
import com.example.user_management_backend.entity.User;
import com.example.user_management_backend.repository.OtpRepository;
import com.example.user_management_backend.repository.UserRepository;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;


@Slf4j
@Service
@AllArgsConstructor
public class EmailServiceImpl {

    private JavaMailSender mailSender;
    private UserRepository userRepository;
    private OtpRepository otpRepository;
        private BCryptPasswordEncoder encoder;

    public String sendOtp(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("my.custome.projects.2025@gmail.com");
            helper.setTo(email);
            helper.setSubject("OTP Verification");

            String htmlContent;
            try (var inputStream = Objects.requireNonNull(
                    JwtAuthenticationController.class.getResourceAsStream("/templates/otp-template.html"))) {
                htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

            htmlContent = htmlContent.replace("{{OTP}}", otp);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            return "OTP email sent successfully!";
        } catch (Exception e) {
            return "Failed to send OTP email: " + e.getMessage();
        }
    }

    public String sendAccountCreationEmail(User user) {
        log.info("Starting to send account creation email to {}", user.getEmail());

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("your.email@example.com");
            helper.setTo(user.getEmail());
            helper.setSubject("Congratulations on Creating Your Account!");

            String htmlContent;
            try (var inputStream = Objects.requireNonNull(
                    EmailServiceImpl.class.getResourceAsStream("/templates/account-creation-template.html"))) {
                htmlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                log.debug("HTML template loaded successfully");
            } catch (Exception e) {
                log.error("Failed to load HTML template", e);
                return "Failed to send account creation email: " + e.getMessage();
            }

            htmlContent = htmlContent.replace("{{id}}", String.valueOf(user.getId()))
                    .replace("{{name}}", user.getName())
                    .replace("{{email}}", user.getEmail())
                    .replace("{{password}}", "xxxxxxxxxxxxxxxxxxxx")
                    .replace("{{role}}", user.getRole())
                    .replace("{{gender}}", user.getGender())
                    .replace("{{address}}", user.getAddress())
                    .replace("{{phone}}", user.getPhone());
            log.debug("HTML content replaced with user details");

            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Account creation email sent successfully to {}", user.getEmail());
            return "Account creation email sent successfully!";
        } catch (Exception e) {
            log.error("Failed to send account creation email to {}", user.getEmail(), e);
            return "Failed to send account creation email: " + e.getMessage();
        }

    }

    
    public String forgot(String email) {
        log.info("Received forgot password request for email: {}", email);

        return userRepository.findByEmail(email)
                .map(data -> {
                    String otp = generateOTP();
                    long time = System.currentTimeMillis() + (5 * 60 * 1000); // 5 minutes later
                    Otp otpEntity = new Otp(null, email, otp, time); // Ensure ID is null for new entity
                    otpRepository.save(otpEntity); // Save the OTP details
                    sendOtp(email, otp); // Send the OTP to the user's email
                    log.info("OTP generated and sent to email: {}", email);
                    return "Check your inbox";
                })
                .orElseGet(() -> {
                    log.warn("Forgot password request for non-existent email: {}", email);
                    return "You are not our customer";
                });
    }

    // public boolean verifyOTP(String email, String otp) {
    //     return otpRepository.findByEmailAndOtp(email, otp)
    //                         .isPresent();
    // }
    
    public boolean verifyOTP(String email, String otp) {
        log.info("Received OTP verification request for email: {}", email);
        System.out.println(otpRepository.findByEmailAndOtp(email, otp));
        boolean isPresent = otpRepository.findByEmailAndOtp(email, otp)
                                         .isPresent();
        
        if (isPresent) {
            log.info("OTP verification successful for email: {}", email);
        } else {
            log.warn("OTP verification failed for email: {}", email);
        }
        
        return isPresent;
    }
    public boolean updatePassword(String email, String otp, String password) {
        return otpRepository.findByEmailAndOtp(email, otp).map(
            data -> {
                System.out.println(data.getOtp());
                User user = userRepository.findByEmail(data.getEmail()).get();
                user.setPassword(encoder.encode(password));
                userRepository.save(user);
                log.info("Password updated successfully for email: {}", email);
                return true;
            }
        ).orElse(false);
    }
    
    @Scheduled(fixedRate = 300000) // runs every 5 minutes
    public void deleteOldOtps() {
        long time = System.currentTimeMillis(); //
        otpRepository.deleteByExpiryTimeBefore(time);
        log.info("Deleted OTPs older than 5 minutes");
    }




    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates a random 6-digit number
        return String.valueOf(otp);
    }



}
