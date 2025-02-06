package com.example.user_management_backend.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.user_management_backend.entity.User;
import com.example.user_management_backend.repository.UserRepository;
import com.example.user_management_backend.util.UserPrincipal;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;


    @Override
    public UserDetails loadUserByUsername(String email)  {
        Optional<User> user = repo.findByEmail(email);
    
        if (!user.isPresent()) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User not with given email "+email);
        }
        return new UserPrincipal(user.get());
    }
    

}
