package com.gestion.banking.controllers;

import com.gestion.banking.dto.TransactionSumDetails;
import com.gestion.banking.services.StatistiqueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/statistics")
@Tag(name = "statistic")
public class StatisticsController {
    private final StatistiqueService statistiqueService;


    public StatisticsController(StatistiqueService statistiqueService) {
        this.statistiqueService = statistiqueService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TransactionSumDetails>> findSumTransaction(
            @PathVariable("id") String id,
            @RequestParam("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
           @RequestParam("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ){
        return ResponseEntity.ok(statistiqueService.findSumTransaction(startDate, endDate , id));
    }

    @GetMapping("/balance/{id}")
    public ResponseEntity<BigDecimal>  getAccountBalance(
            @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(statistiqueService.getAccountBalance(id));
    }
    @GetMapping("/transfert/{id}")
    public ResponseEntity<BigDecimal> highestTransfert(
            @PathVariable("id") String id
    ){
        return ResponseEntity.ok(statistiqueService.highestTransfert(id));
    }

    @GetMapping("/deposite/{id}")
    public ResponseEntity<BigDecimal> highestDeposite(
            @PathVariable("id") String id
    ){
        return ResponseEntity.ok(statistiqueService.highestDeposite(id));
    }
}
