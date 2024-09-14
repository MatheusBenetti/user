package com.totex.user.controller;

import com.totex.user.business.UserService;
import com.totex.user.business.dto.AddressDto;
import com.totex.user.business.dto.PhoneDto;
import com.totex.user.business.dto.UserDto;
import com.totex.user.infrastructure.entity.UserEntity;
import com.totex.user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
        );

        return "Bearer: " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping()
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.updateUser(token, userDto));
    }

    @PutMapping("/address")
    public ResponseEntity<AddressDto> updateAddress(@RequestBody AddressDto addressDto, @RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.updateAddress(id, addressDto));
    }

    @PutMapping("/phone")
    public ResponseEntity<PhoneDto> updatePhone(@RequestBody PhoneDto phoneDto, @RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.updatePhone(id, phoneDto));
    }

    @PostMapping("/address")
    public ResponseEntity<AddressDto> registerAddress(@RequestBody AddressDto addressDto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.registerAddress(token, addressDto));
    }

    @PostMapping("/phone")
    public ResponseEntity<PhoneDto> registerPhone(@RequestBody PhoneDto phoneDto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.registerPhone(token, phoneDto));
    }
}
