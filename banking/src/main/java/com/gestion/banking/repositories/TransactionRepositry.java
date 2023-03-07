package com.gestion.banking.repositories;

import com.gestion.banking.dto.TransactionSumDetails;
import com.gestion.banking.enums.TransactionType;
import com.gestion.banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface TransactionRepositry extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByUserId(String id);

    @Query(value = "select sum(t.amount) from Transaction t where t.user.id =:id")
    BigDecimal findAccountBalance(@Param(value = "id") String id);

    @Query("select max(t.amount) as amount from  Transaction t where t.user.id =:id and t.transactionType =:transactionType")
    BigDecimal findHighestAccountByTransactionType(String id, TransactionType transactionType);

    @Query("select t.transactionDate as transactionDate, sum (t.amount) as amount from Transaction t where t.user.id =:id and t.dateCreate " +
            "between " +
            ":start and :end " +
            "group by t.transactionDate")
    List<TransactionSumDetails> findSumTransactionByDate(LocalDateTime start, LocalDateTime end, String id);
}
