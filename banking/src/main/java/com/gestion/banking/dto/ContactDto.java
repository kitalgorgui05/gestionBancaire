package com.gestion.banking.dto;

import com.gestion.banking.model.Contact;
import com.gestion.banking.model.User;
import lombok.*;
import org.jcp.xml.dsig.internal.dom.ApacheTransform;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDto {
    private String id;
    private String firtName;
    private String lastName;
    private String email;
    private String iban;

    private String userId;
    public static ContactDto toDto(Contact contact){
        return ContactDto.builder()
                .id(contact.getId())
                .firtName(contact.getFirtName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .iban(contact.getIban())
                .userId(contact.getUser().getId())
            .build();
    }

    public static Contact toEntity(ContactDto contact){
        return Contact.builder()
                .id(contact.getId())
                .firtName(contact.getFirtName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .iban(contact.getIban())
                .user(
                        User.builder()
                                .id(contact.getId())
                                .build()
                )
                .build();
    }
}
