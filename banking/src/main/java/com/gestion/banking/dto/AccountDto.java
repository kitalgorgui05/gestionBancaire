package com.gestion.banking.dto;

import com.gestion.banking.model.Account;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private String id;
    private String iban;
    private UserDto user;

    public static AccountDto formEntity(Account account){
        return AccountDto.builder()
                .id(account.getId())
                .iban(account.getIban())
                .user(UserDto.toDto(account.getUser()))
                .build();
    }
    public static Account toEntity(AccountDto acount){
        return Account.builder()
                .id(acount.getId())
                .iban(acount.getIban())
                .user(UserDto.toEntity(acount.getUser()))
                .build();
    }
}
