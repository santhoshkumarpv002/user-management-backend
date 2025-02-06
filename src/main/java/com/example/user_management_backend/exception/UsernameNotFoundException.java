package com.example.user_management_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UsernameNotFoundException extends RuntimeException {

   

    public UsernameNotFoundException(String string) {
     

        super(string);
    }

}


