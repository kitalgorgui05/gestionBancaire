package com.gestion.banking.dto;

import com.gestion.banking.model.Address;
import com.gestion.banking.model.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private String id;
    private String street;
    private Integer houseNumber;
    private Integer zipCode;
    private String city;
    private String country;

    private String userId;

    public static  AddressDto toDto(Address address){
        return AddressDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .country(address.getCountry())
                .userId(address.getUser().getId())
                .build();
    }
    public static  Address toEntity(AddressDto address){
        return Address.builder()
                .id(address.getId())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .country(address.getCountry())
                .user(
                        User.builder()
                                .id(address.getId())
                                .build()
                )
                .build();
    }
}
