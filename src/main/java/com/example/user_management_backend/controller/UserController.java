package com.example.user_management_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_management_backend.dto.ResponseDto;
import com.example.user_management_backend.dto.UserDto;
import com.example.user_management_backend.service.UserService;
import com.example.user_management_backend.util.Constants;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")

@AllArgsConstructor
public class UserController {

    private UserService userService;

    // @GetMapping("/{id}")
    // public ResponseEntity<UserDto> getUser(@PathVariable long id){
    // UserDto userdto=userService.getUser(id);
    // return ResponseEntity.ok(userdto);
    //
    // }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAlluser() {
        // System.out.println("inside getAlluser");
        List<UserDto> userdtos = userService.getAllUsers();
        return ResponseEntity.ok(userdtos);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<UserDto>> getById(@PathVariable String email) {
        UserDto existingUser = userService.getUser(email);
        List<UserDto> userdtos = List.of(existingUser); // Convert to List<UserDto
        return ResponseEntity.ok(userdtos);
    }

    @PutMapping("/update/{email}")
    public  ResponseEntity<UserDto> updateUser(@PathVariable String email,@Valid @RequestBody UserDto userDto) {
      UserDto updatedUserDAta= userService.updateUser(email, userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(updatedUserDAta);
    }

     public ResponseEntity<ResponseDto> addUser(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(Constants.STATUS_201, Constants.MESSAGE_201));
    }


    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok("Successfully used deleted!");
    }

}
