package com.example.user_management_backend.service;



import java.util.List;

import com.example.user_management_backend.dto.UserDto;

public interface UserService {


    UserDto saveUser(UserDto userDto);

    UserDto getUser(String email);

    List<UserDto> getAllUsers();

    UserDto updateUser(String email, UserDto userDto);

    void deleteUser(String email);


}
