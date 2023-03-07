package com.gestion.banking.repositories;

import com.gestion.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByIban(String iban);

    Optional<Account> findByUserId(String id);
}
