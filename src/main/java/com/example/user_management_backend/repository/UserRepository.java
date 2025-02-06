package com.example.user_management_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.user_management_backend.entity.User;


public interface UserRepository extends JpaRepository<User,Long>{
@Transactional
Optional<User> findByEmail(String email);
// User findByUsername(String username);

}
