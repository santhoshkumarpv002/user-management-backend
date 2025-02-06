package com.example.user_management_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserManagementBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementBackEndApplication.class, args);
	}

}
