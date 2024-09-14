package com.totex.user.business.converter;

import com.totex.user.business.dto.AddressDto;
import com.totex.user.business.dto.PhoneDto;
import com.totex.user.business.dto.UserDto;
import com.totex.user.infrastructure.entity.AddressEntity;
import com.totex.user.infrastructure.entity.PhoneEntity;
import com.totex.user.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {

    /*
        Without using builder:
        public UserEntity convertToUser(UserDto userDto) {
            UserEntity user = new UserEntity();
            user.setEmail(userDto.getEmail());
            user.name(userDto.getName());...
        }
     */

    public UserEntity convertToUser(UserDto userDto) {
        return UserEntity.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .addresses(userDto.getAddresses() != null ? convertToListAddress(userDto.getAddresses()) : null)
                .phoneNumbers(userDto.getPhoneNumbers() != null ? convertToListPhones(userDto.getPhoneNumbers()) : null         )
                .build();
    }

    /*
        Inside convertToListAddress you can use this or the .stream().map()
        List<AddressEntity> addresses = new ArrayList<>();
        for (AddressDto address : addressDto) {
            addresses.add(convertToAddress(address));
        }
        return addresses;
    */

    public List<AddressEntity> convertToListAddress(List<AddressDto> addressDto) {
        return addressDto.stream().map(this::convertToAddress).toList();
    }

    public AddressEntity convertToAddress(AddressDto addressDto) {
        return AddressEntity.builder()
                .state(addressDto.getState())
                .city(addressDto.getCity())
                .zipCode(addressDto.getZipCode())
                .number(addressDto.getNumber())
                .street(addressDto.getStreet())
                .complement(addressDto.getComplement())
                .build();
    }

    public List<PhoneEntity> convertToListPhones(List<PhoneDto> phoneDto) {
        return phoneDto.stream().map(this::convertToPhone).toList();
    }

    public PhoneEntity convertToPhone(PhoneDto phoneDto) {
        return PhoneEntity.builder()
                .number(phoneDto.getNumber())
                .ddd(phoneDto.getDdd())
                .build();
    }

    // Convert back to Dto

    public UserDto convertToUserDto(UserEntity user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .addresses(convertToListAddressDto(user.getAddresses()))
                .phoneNumbers(convertToListPhonesDto(user.getPhoneNumbers()))
                .build();
    }

    public List<AddressDto> convertToListAddressDto(List<AddressEntity> address) {
        return address.stream().map(this::convertToAddressDto).toList();
    }

    public AddressDto convertToAddressDto(AddressEntity address) {
        return AddressDto.builder()
                .id(address.getId())
                .state(address.getState())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .number(address.getNumber())
                .street(address.getStreet())
                .complement(address.getComplement())
                .build();
    }

    public List<PhoneDto> convertToListPhonesDto(List<PhoneEntity> phone) {
        return phone.stream().map(this::convertToPhoneDto).toList();
    }

    public PhoneDto convertToPhoneDto(PhoneEntity phone) {
        return PhoneDto.builder()
                .id(phone.getId())
                .number(phone.getNumber())
                .ddd(phone.getDdd())
                .build();
    }


    // Update user
    public UserEntity updateUser(UserDto userDto, UserEntity userEntity) {
        return UserEntity.builder()
                .name(userDto.getName() != null ? userDto.getName() : userEntity.getName())
                .email(userDto.getEmail() != null ? userDto.getEmail() : userEntity.getEmail())
                .password(userDto.getPassword() != null ? userDto.getPassword() : userEntity.getPassword())
                .id(userEntity.getId())
                .addresses(userEntity.getAddresses())
                .phoneNumbers(userEntity.getPhoneNumbers())
                .build();
    }

    public AddressEntity updateAddress(AddressDto addressDto, AddressEntity addressEntity) {
        return AddressEntity.builder()
                .id(addressDto.getId())
                .city(addressDto.getCity() != null ? addressDto.getCity() : addressEntity.getCity())
                .zipCode(addressDto.getZipCode() != null ? addressDto.getZipCode() : addressEntity.getZipCode())
                .complement(addressDto.getComplement() != null ? addressDto.getComplement() : addressEntity.getComplement())
                .number(addressDto.getNumber() != null ? addressDto.getNumber() : addressEntity.getNumber())
                .street(addressDto.getStreet() != null ? addressDto.getStreet() : addressEntity.getStreet())
                .state(addressDto.getState() != null ? addressDto.getState() : addressEntity.getState())
                .build();
    }

    public PhoneEntity updatePhone(PhoneDto phoneDto, PhoneEntity phoneEntity) {
        return PhoneEntity.builder()
                .id(phoneDto.getId())
                .ddd(phoneDto.getDdd() != null ? phoneDto.getDdd() : phoneEntity.getDdd())
                .number(phoneDto.getNumber() != null ? phoneDto.getNumber() : phoneEntity.getNumber())
                .build();
    }

    public AddressEntity convertToAddressEntity(AddressDto addressDto, Long userId) {
        return AddressEntity.builder()
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .zipCode(addressDto.getZipCode())
                .number(addressDto.getNumber())
                .complement(addressDto.getComplement())
                .street(addressDto.getStreet())
                .userId(userId)
                .build();
    }

    public PhoneEntity convertToPhoneEntity(PhoneDto phoneDto, Long userId) {
        return PhoneEntity.builder()
                .number(phoneDto.getNumber())
                .ddd(phoneDto.getDdd())
                .userId(userId)
                .build();
    }
}
