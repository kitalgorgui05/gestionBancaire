package com.gestion.banking.dto;

import com.gestion.banking.enums.TransactionType;
import com.gestion.banking.model.Transaction;
import com.gestion.banking.model.User;
import lombok.*;
import org.springframework.core.annotation.Order;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class TransactionDto {
    private String id;
    @Positive
    private BigDecimal amount; // sold
    private TransactionType transactionType; // enumerateur depot ou retrait
    private LocalDate transactionDate;
    private String destinationIban;
    private String user;


    public static  TransactionDto toDto(Transaction transaction){
        return TransactionDto.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .transactionDate(transaction.getTransactionDate())
                .destinationIban(transaction.getDestinationIban())
                .user(transaction.getUser().getId())
                .build();
    }

    public static  Transaction toEntity(TransactionDto transaction){
        return Transaction.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .transactionDate(LocalDate.now())
                .destinationIban(transaction.getDestinationIban())
                .user(
                        User.builder()
                                .id(transaction.getUser())
                                .build()
                )
                .build();
    }
}
