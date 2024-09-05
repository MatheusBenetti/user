package com.totex.user.business;

import com.totex.user.business.converter.UserConverter;
import com.totex.user.business.dto.UserDto;
import com.totex.user.infrastructure.entity.UserEntity;
import com.totex.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDto saveUser(UserDto userDto) {
        UserEntity user = userConverter.convertToUser(userDto);
        return userConverter.convertToUserDto(userRepository.save(user));
    }
}
