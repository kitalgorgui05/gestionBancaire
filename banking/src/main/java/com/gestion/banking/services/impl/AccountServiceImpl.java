package com.gestion.banking.services.impl;

import com.gestion.banking.dto.AccountDto;
import com.gestion.banking.exceptions.OperationNotPermitteException;
import com.gestion.banking.model.Account;
import com.gestion.banking.repositories.AccountRepository;
import com.gestion.banking.services.AccountService;
import com.gestion.banking.validators.ObjectsValidator;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ObjectsValidator<AccountDto> validator;

    public AccountServiceImpl(AccountRepository accountRepository, ObjectsValidator<AccountDto> validator) {
        this.accountRepository = accountRepository;
        this.validator = validator;
    }

    @Override
    public String save(AccountDto dto) {
        validator.validate(dto);
        Account account= AccountDto.toEntity(dto);
        boolean userHasAlreadyAnAccount=accountRepository.findByUserId(account.getUser().getId()).isPresent();
        // condition valide ( userHasAlreadyAnAccount && account.getUser().isActive() ) a revoir
        if(userHasAlreadyAnAccount && account.getUser().isActive()){
            throw new OperationNotPermitteException(
                    "The selected user has already an active account",
                    "Create account",
                    "Account service",
                    "Account creation"
            );
        }
        if (dto.getId()==null){
            account.setIban(generateRandomIban());
        }
        return accountRepository.saveAndFlush(account).getId();
    }

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll()
                .stream().map(AccountDto::formEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findById(String id) {
        return accountRepository.findById(id).map(AccountDto::formEntity).orElseThrow(
                ()->new EntityNotFoundException("No ccount was found with the ID :"+id)
        );
    }

    @Override
    @Transactional
    public void delete(String id) {
        accountRepository.deleteById(id);
    }

    private String generateRandomIban(){
        String iban=Iban.random(CountryCode.DE).toFormattedString();
        boolean ibanExists = accountRepository.findByIban(iban).isPresent();
        if(ibanExists){
            generateRandomIban();
        }
        return iban;
    }
}
