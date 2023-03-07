package com.gestion.banking.services.impl;

import com.gestion.banking.dto.TransactionDto;
import com.gestion.banking.enums.TransactionType;
import com.gestion.banking.model.Transaction;
import com.gestion.banking.repositories.TransactionRepositry;
import com.gestion.banking.services.TransactionService;
import com.gestion.banking.validators.ObjectsValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepositry transactionRepositry;
    private final ObjectsValidator<TransactionDto> validator;

    public TransactionServiceImpl(TransactionRepositry transactionRepositry, ObjectsValidator<TransactionDto> validator) {
        this.transactionRepositry = transactionRepositry;
        this.validator = validator;
    }
    @Override
    @Transactional
    public String save(TransactionDto dto) {
        validator.validate(dto);

        Transaction transaction = TransactionDto.toEntity(dto);
        BigDecimal transactionMultiplier = BigDecimal.valueOf(getTransactionMultiplier(transaction.getTransactionType()));
        BigDecimal amount = transaction.getAmount().multiply(transactionMultiplier);
        transaction.setAmount(amount);
        return transactionRepositry.saveAndFlush(transaction).getId();
    }

    @Override
    public List<TransactionDto> findAll() {
        return transactionRepositry.findAll()
                .stream()
                .map(TransactionDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto findById(String id) {
        return transactionRepositry.findById(id)
                .map(TransactionDto::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No Transaction was found with ID :" +id));
    }

    @Override
    @Transactional
    public void delete(String id) {
        transactionRepositry.deleteById(id);
    }

    private int getTransactionMultiplier(TransactionType type){
        return TransactionType.TRANSFERT ==type ? -1 : 1;
    }

    @Override
    public List<TransactionDto> findAllByUserId(String id) {
        return transactionRepositry.findAllByUserId(id)
                .stream()
                .map(TransactionDto::toDto)
                .collect(Collectors.toList());
    }
}
