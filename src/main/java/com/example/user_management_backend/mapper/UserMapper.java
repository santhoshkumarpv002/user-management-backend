package com.example.user_management_backend.mapper;


import com.example.user_management_backend.dto.UserDto;
import com.example.user_management_backend.entity.User;

public class UserMapper {

    public static UserDto mapToDto (User user){
        UserDto userDto=new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getGender(),
                user.getAddress(),
                user.getPhone()
        );

        return  userDto;

    }


    public static  User maptoUser(UserDto userDto){
        User user=new User(
            userDto.getId(),
                userDto.getName(),
                userDto.getEmail().toLowerCase(),
                userDto.getPassword(),
                userDto.getRole().toUpperCase(),
                userDto.getGender(),
                userDto.getAddress(),
                userDto.getPhone()
        );
        return user;
    }



}
