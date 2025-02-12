package com.example.user_management_backend.service.Impl;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user_management_backend.dto.UserDto;
import com.example.user_management_backend.entity.User;
import com.example.user_management_backend.exception.ResourceAlreadyExistException;
import com.example.user_management_backend.exception.UsernameNotFoundException;
import com.example.user_management_backend.mapper.UserMapper;
import com.example.user_management_backend.repository.UserRepository;
import com.example.user_management_backend.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private EmailServiceImpl emailSerice;
    private BCryptPasswordEncoder encoder;

    @Override
public UserDto saveUser(UserDto userDto) {
    log.info("Checking if user with email {} exists", userDto.getEmail());

    userRepository.findByEmail(userDto.getEmail())
        .ifPresentOrElse(
            (existingUser) -> {
                log.warn("User with email {} already exists", userDto.getEmail());
                throw new ResourceAlreadyExistException("User already exists with this Email: " + userDto.getEmail());
            },
            () -> {
                log.info("Saving new user with email {}", userDto.getEmail());
                User newUser = UserMapper.maptoUser(userDto);
                newUser.setPassword(encoder.encode(newUser.getPassword()));
                newUser.setRole(newUser.getRole().toUpperCase());
                User savedUser = userRepository.save(newUser);
                emailSerice.sendAccountCreationEmail(savedUser);
                log.info("User with email {} saved successfully with ID {}", userDto.getEmail(), savedUser.getId());
                
                // Uncomment and complete the email service call if needed
                // emailService.sendCredentials(savedUser.getId(), savedUser.getEmail(), savedUser.getName());
            }
        );

    return UserMapper.mapToDto(userRepository.findByEmail(userDto.getEmail()).get());
}



    // @Override
    // public UserDto getUser(long id) {
    // return userRepository.findById(id)
    // .map(UserMapper::mapToDto) // Using method reference here
    // .orElseThrow(
    // () -> new ResourceNotFoundException("User not found with id: " + id);
    // );
    // }
    @Override
    public UserDto getUser(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::mapToDto) // Using method reference here
                .orElseThrow(() -> new UsernameNotFoundException("User not found with Given Email id: " +email)); // Corrected this
                                                                                                    // line
    }

    @Override
    public List<UserDto> getAllUsers() {

        return userRepository.findAll()
                .stream().map(UserMapper::mapToDto).toList();
    }

    @Override
    public UserDto updateUser(String email, UserDto userDto) {
        return userRepository.findByEmail(email).map(existingUser -> {

            // existingUser.setId(userDto.getId());
            existingUser.setName(userDto.getName());
            existingUser.setEmail(userDto.getEmail());
            // existingUser.setPassword(userDto.getPassword());
            existingUser.setRole(userDto.getRole().toUpperCase());
            existingUser.setGender(userDto.getGender());
            existingUser.setAddress(userDto.getAddress());
            existingUser.setPhone(userDto.getPhone());
            // Update other fields as necessary
            User updatedUser = userRepository.save(existingUser);
            return UserMapper.mapToDto(updatedUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + userDto.getId()));

    }

    @Override
    public void deleteUser(String email) {
        userRepository.findByEmail(email)
                .map(user -> {
                    userRepository.deleteById(user.getId());
                    return user; // return is required to satisfy the map function
                })
                .orElseThrow(() -> new RuntimeException("User doesn't exist with the given Email Id: " +  email));
    }

}