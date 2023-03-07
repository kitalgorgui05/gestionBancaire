package com.gestion.banking.dto;

import com.gestion.banking.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDto {
    private String id;
    private String firtName;
    private String lastName;
    private String email;
    private String password;

    public static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .firtName(user.getFirtName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static User toEntity(UserDto user){
        return User.builder()
                .id(user.getId())
                .firtName(user.getFirtName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}
