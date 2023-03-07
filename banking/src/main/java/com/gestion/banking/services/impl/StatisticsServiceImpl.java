package com.gestion.banking.services.impl;

import com.gestion.banking.dto.TransactionSumDetails;
import com.gestion.banking.enums.TransactionType;
import com.gestion.banking.repositories.TransactionRepositry;
import com.gestion.banking.services.StatistiqueService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatistiqueService {

    private final TransactionRepositry transactionRepositry;

    public StatisticsServiceImpl(TransactionRepositry transactionRepositry) {
        this.transactionRepositry = transactionRepositry;
    }

    @Override
    public List<TransactionSumDetails> findSumTransaction(LocalDate startDate, LocalDate endDate, String id) {
        LocalDateTime start =LocalDateTime.of(startDate, LocalTime.of(0,0,0));
        LocalDateTime end =LocalDateTime.of(startDate, LocalTime.of(23,59,59));
        return transactionRepositry.findSumTransactionByDate(start,end,id);
    }

    @Override
    public BigDecimal getAccountBalance(String id) {
        return transactionRepositry.findAccountBalance(id);
    }

    @Override
    public BigDecimal highestTransfert(String id) {
        return transactionRepositry.findHighestAccountByTransactionType(id, TransactionType.TRANSFERT);
    }

    @Override
    public BigDecimal highestDeposite(String id) {
        return transactionRepositry.findHighestAccountByTransactionType(id, TransactionType.DEPOT);
    }
}
