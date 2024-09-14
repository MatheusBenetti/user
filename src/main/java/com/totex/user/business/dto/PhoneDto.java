package com.totex.user.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDto {

    private Long id;

    private String number;

    private String ddd;
}
