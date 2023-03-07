package com.gestion.banking.services;

import com.gestion.banking.dto.TransactionDto;

import java.util.List;

public interface TransactionService extends AbstractService<TransactionDto>{
    List<TransactionDto> findAllByUserId(String id);
}
