package com.gestion.banking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity{
    private String street;  // la rue
    private Integer houseNumber;
    private Integer zipCode; // code postale
    private String city;
    private String country;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;
}
