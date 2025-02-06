package com.example.user_management_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.user_management_backend.entity.Otp;

import jakarta.transaction.Transactional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmailAndOtp(String email, String otp);
    @Transactional
    @Modifying
    void deleteByExpiryTimeBefore(long expiryTime);

    @Transactional
    @Modifying
    void deleteByEmail(String email);
}
