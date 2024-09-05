package com.totex.user.business.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String name;

    private String email;

    private String password;

    private List<AddressDto> addresses;

    private List<PhoneDto> phoneNumbers;
}
