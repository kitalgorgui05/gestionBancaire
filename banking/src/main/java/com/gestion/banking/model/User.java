package com.gestion.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.asm.MemberSubstitution;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @NotBlank
    private String firtName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    @Size(min = 8, max = 256, message = "Le mot de passe doit etre entre 8 et 10 caractere")
    private String password;
    private Boolean active;
    @OneToOne
    private Address address;

    @OneToOne
    private Role role;

    @OneToOne
    private Account account;
    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;
    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    public boolean isActive() {
        return this.active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
