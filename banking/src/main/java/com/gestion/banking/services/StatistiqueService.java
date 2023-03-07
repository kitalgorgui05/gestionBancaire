package com.gestion.banking.services;

import com.gestion.banking.dto.TransactionSumDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StatistiqueService {
    List<TransactionSumDetails> findSumTransaction(LocalDate startDate, LocalDate endDate, String id);
    BigDecimal  getAccountBalance(String id);

    BigDecimal highestTransfert(String id);

    BigDecimal highestDeposite(String id);
}
