package com.gestion.banking.model;

import com.gestion.banking.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends BaseEntity {
    private BigDecimal amount; // sold
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // enumerateur depot ou retrait
    @Column(updatable = false)
    private LocalDate transactionDate;
    private String destinationIban;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

}
