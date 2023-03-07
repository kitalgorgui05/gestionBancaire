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
@Builder
public class Role extends BaseEntity{
    private String name;
    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;
}
