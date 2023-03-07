package com.gestion.banking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends BaseEntity{
    private String firtName;
    private String lastName;
    private String email;
    private String iban;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
