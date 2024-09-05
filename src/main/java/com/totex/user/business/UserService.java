package com.totex.user.business;

import com.totex.user.business.converter.UserConverter;
import com.totex.user.business.dto.UserDto;
import com.totex.user.infrastructure.entity.UserEntity;
import com.totex.user.infrastructure.exceptions.ConflictException;
import com.totex.user.infrastructure.exceptions.ResourceNotFoundException;
import com.totex.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UserDto saveUser(UserDto userDto) {
        emailExists(userDto.getEmail());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity user = userConverter.convertToUser(userDto);
        return userConverter.convertToUserDto(userRepository.save(user));
    }

    public void emailExists(String email) {
        try {
            boolean exists = verifyExistentEmail(email);

            if (exists) {
                throw new ConflictException("Email already exists.");
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email already exists." + e.getCause());
        }
    }

    public boolean verifyExistentEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found."));
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}
