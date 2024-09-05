package com.totex.user.business.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private String street;

    private int number;

    private String complement;

    private String city;

    private String state;

    private String zipCode;
}
