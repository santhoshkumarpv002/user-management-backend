package com.example.user_management_backend.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_management_backend.dto.ErrorResponseDto;
import com.example.user_management_backend.dto.ResponseDto;
import com.example.user_management_backend.dto.UserDto;
import com.example.user_management_backend.dto.VerifyOtpDto;
import com.example.user_management_backend.service.UserService;
import com.example.user_management_backend.service.Impl.EmailServiceImpl;
import com.example.user_management_backend.service.Impl.JwtService;
import com.example.user_management_backend.service.Impl.MyUserDetailsService;
import com.example.user_management_backend.util.Constants;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j


@RestController

@RequestMapping("/api/jwt")
@AllArgsConstructor
public class JwtAuthenticationController {

    private JwtService jwtService;
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private EmailServiceImpl emailService;
    private MyUserDetailsService userDetailsService; // Your user repository

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
    // log.info("Inside login controller");
    // log.info("Email: {}", user.get("email"));
    // log.info("Password: {}", user.get("password"));

    // try {
    // // Load user details
    // UserDetails userDetails =
    // userDetailsService.loadUserByUsername(user.get("email"));

    // // Check if user exists before attempting authentication
    // if (userDetails == null) {
    // log.error("User not found: {}", user.get("email"));
    // throw new UsernameNotFoundException("User not found");
    // }

    // // Attempt authentication
    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(user.get("email"),
    // user.get("password"))
    // );

    // if (authentication.isAuthenticated()) {
    // return ResponseEntity.ok(new ResponseDto(Constants.STATUS_200,
    // Constants.MESSAGE_200));
    // } else {
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    // .body(new ErrorResponseDto("/login", HttpStatus.UNAUTHORIZED, "Login Failed",
    // LocalDateTime.now()));
    // }
    // } catch (UsernameNotFoundException e) {
    // log.error("User not found: {}", e.getMessage());
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body(new ErrorResponseDto("/login", HttpStatus.NOT_FOUND, "User not found",
    // LocalDateTime.now()));
    // } catch (BadCredentialsException e) {
    // log.error("Invalid credentials: {}", e.getMessage());
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(new ErrorResponseDto("/login", HttpStatus.BAD_REQUEST, "Invalid
    // credentials", LocalDateTime.now()));
    // } catch (Exception e) {
    // log.error("An error occurred: {}", e.getMessage());
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(new ErrorResponseDto("/login", HttpStatus.INTERNAL_SERVER_ERROR, "An
    // error occurred: " + e.getMessage(), LocalDateTime.now()));
    // }
    // }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
        log.info("Inside login controller");
        log.info("Email: {}", user.get("email"));
        log.info("Password: {}", user.get("password"));

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.get("email"));

        // Attempt authentication
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.get("email"), user.get("password")));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.get("email"));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);

            ResponseDto response = new ResponseDto(Constants.STATUS_200, Constants.MESSAGE_200);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(response);
            // return ResponseEntity.ok(new ResponseDto(Constants.STATUS_200,
            // Constants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponseDto("/login", HttpStatus.UNAUTHORIZED, "Login Failed", LocalDateTime.now()));
        }
    }

    // @PostMapping("login")
    // public String login(@RequestBody Map<String,String> user){

    // Authentication authentication = authenticationManager
    // .authenticate(new UsernamePasswordAuthenticationToken(user.get("email"),
    // user.get("password")));

    // if(authentication.isAuthenticated())
    // return jwtService.generateToken(user.get("email"));
    // else
    // return "Login Failed";

    // }

    // @PostMapping("/login")
    // public ResponseEntity<String> login(@RequestBody Map<String, String> user) {

    // log.info("Inside login controller");
    // log.info("Email: {}", user.get("email"));
    // log.info("Password: {}", user.get("password"));
    // try {
    // // Load user details
    // UserDetails userDetails =
    // userDetailsService.loadUserByUsername(user.get("email"));

    // // Attempt authentication
    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(user.get("email"),
    // user.get("password"))
    // );

    // if (authentication.isAuthenticated()) {
    // return ResponseEntity.ok(jwtService.generateToken(user.get("email")));
    // } else {
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
    // }
    // } catch (UsernameNotFoundException e) {
    // // Log the exception
    // System.out.println("User not found: " + e.getMessage());
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    // } catch (BadCredentialsException e) {
    // // Log the exception
    // System.out.println("Invalid credentials: " + e.getMessage());
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid
    // credentials");
    // } catch (Exception e) {
    // // Log the exception
    // e.printStackTrace();
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error
    // occurred: " + e.getMessage());
    // }
    // }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> addUser(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
    }

    @GetMapping("/forgot/{email}")
    public ResponseEntity<Map<String, String>> sendEmail(@PathVariable String email) {
        String msg = emailService.forgot(email);
        Map<String, String> response = new HashMap<>();
        response.put("message", msg);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<Map<String, Boolean>> verifyOTP(@RequestBody VerifyOtpDto verifyData) {
        log.info("Inside controller layer of emailController");
        log.info("Email: {}", verifyData.getEmail());
        log.info("OTP: {}", verifyData.getOtp());

        boolean value = emailService.verifyOTP(verifyData.getEmail(), verifyData.getOtp());
        Map<String, Boolean> response = new HashMap<>();
        response.put("message", value);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<Map<String, Boolean>> verifyOTP(@RequestBody Map<String, String> data) {
        System.out.println("inside controller layer- of emailController");
        System.out.println(data.get("email"));
        System.out.println(data.get("otp"));
        System.out.println(data.get("password"));

        boolean msg = emailService.updatePassword(data.get("email"), data.get("otp"), data.get("password"));

        Map<String, Boolean> response = new HashMap<>();
        response.put("message", msg);
        return ResponseEntity.ok(response);
    }

    // @PostMapping("/verifyOtp")
    // public ResponseEntity<Map<String, String>> verifyOTP(@RequestBody Map<String,
    // String> verifyData) {
    // System.out.println("inside controller layer- of emailController");
    // System.out.println(verifyData.get("email"));
    // System.out.println(verifyData.get("otp"));

    // String msg = forgotEmailService.verifyOTP(verifyData.get("email"),
    // verifyData.get("otp"));
    // Map<String, String> response = new HashMap<>();
    // response.put("message", msg);
    // return ResponseEntity.ok(response);
    // }

    // @PostMapping("/sendOtp")
    // public ResponseEntity<Map<String, Object>> sendOtp(@RequestBody Map<String,
    // Object> requestData) {
    // System.out.println("Inside the sendOtp method");
    // System.out.println("From Email: " + requestData.get("fromEmail"));
    // System.out.println("From Name: " + requestData.get("fromName"));
    // System.out.println("To Emails: " + requestData.get("toEmails"));
    // System.out.println("Subject: " + requestData.get("subject"));
    // System.out.println("Template ID: " + requestData.get("templateId"));
    // System.out.println("Variables: " + requestData.get("variables"));

    // String msg = emailService.sendOtp(
    // requestData.get("fromEmail").toString(),
    // requestData.get("fromName").toString(),
    // (List<String>) requestData.get("toEmails"),
    // requestData.get("subject").toString(),
    // requestData.get("templateId").toString(),
    // (Map<String, Object>) requestData.get("variables")
    // );

    // Map<String, String> response = new HashMap<>();
    // response.put("message", msg);
    // return ResponseEntity.ok(response);
    // }
}
