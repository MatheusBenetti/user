package com.totex.user.business.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private Long id;

    private String street;

    private Long number;

    private String complement;

    private String city;

    private String state;

    private String zipCode;
}
