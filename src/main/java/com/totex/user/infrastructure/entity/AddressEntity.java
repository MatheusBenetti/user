package com.totex.user.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street", length = 100)
    private String street;

    @Column(name = "number", length = 10)
    private Long number;

    @Column(name = "complement", length = 100)
    private String complement;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "zipCode", length = 9)
    private String zipCode;

    @Column(name = "user_id")
    private Long userId;

}
