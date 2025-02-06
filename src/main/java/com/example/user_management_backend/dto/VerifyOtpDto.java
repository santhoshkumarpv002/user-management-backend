package com.example.user_management_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor
public class VerifyOtpDto {

    private String email;
    private  String otp;


}
