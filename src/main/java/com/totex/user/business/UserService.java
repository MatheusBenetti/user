package com.totex.user.business;

import com.totex.user.business.converter.UserConverter;
import com.totex.user.business.dto.AddressDto;
import com.totex.user.business.dto.PhoneDto;
import com.totex.user.business.dto.UserDto;
import com.totex.user.infrastructure.entity.AddressEntity;
import com.totex.user.infrastructure.entity.PhoneEntity;
import com.totex.user.infrastructure.entity.UserEntity;
import com.totex.user.infrastructure.exceptions.ConflictException;
import com.totex.user.infrastructure.exceptions.ResourceNotFoundException;
import com.totex.user.infrastructure.repository.AddressRepository;
import com.totex.user.infrastructure.repository.PhoneRepository;
import com.totex.user.infrastructure.repository.UserRepository;
import com.totex.user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;

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

    public UserDto findUserByEmail(String email) {
        try {
            return userConverter.convertToUserDto(userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found.")));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email not found." + e.getCause());
        }
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public UserDto updateUser(String token, UserDto userDto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));

        userDto.setPassword(userDto.getPassword() != null ? passwordEncoder.encode(userDto.getPassword()) : null);

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found."));

        UserEntity user = userConverter.updateUser(userDto, userEntity);

        return userConverter.convertToUserDto(userRepository.save(user));
    }

    public AddressDto updateAddress(Long addressId, AddressDto addressDto) {
        AddressEntity addressEntity = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Id not found."));

        AddressEntity address = userConverter.updateAddress(addressDto, addressEntity);

        return userConverter.convertToAddressDto(addressRepository.save(address));
    }

    public PhoneDto updatePhone(Long phoneId, PhoneDto phoneDto) {
        PhoneEntity phoneEntity = phoneRepository.findById(phoneId).orElseThrow(() -> new ResourceNotFoundException("Id not found."));

        PhoneEntity phone = userConverter.updatePhone(phoneDto, phoneEntity);

        return userConverter.convertToPhoneDto(phoneRepository.save(phone));
    }

    public AddressDto registerAddress(String token, AddressDto addressDto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found."));
        AddressEntity address = userConverter.convertToAddressEntity(addressDto, user.getId());
        return userConverter.convertToAddressDto(addressRepository.save(address));
    }

    public PhoneDto registerPhone(String token, PhoneDto phoneDto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found."));
        PhoneEntity phone = userConverter.convertToPhoneEntity(phoneDto, user.getId());
        return userConverter.convertToPhoneDto(phoneRepository.save(phone));
    }
}
