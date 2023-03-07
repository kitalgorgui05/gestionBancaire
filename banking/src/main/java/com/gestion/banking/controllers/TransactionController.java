package com.gestion.banking.controllers;

import com.gestion.banking.dto.TransactionDto;
import com.gestion.banking.services.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@Tag(name = "transaction")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/")
    public ResponseEntity<String> save(
            @RequestBody TransactionDto transactionDto
    ){
        return ResponseEntity.ok(transactionService.save(transactionDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionDto>> findAll(){
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> findById(String id){
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<TransactionDto>> findAllByUserId(
            @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(transactionService.findAllByUserId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id
    ) {
        transactionService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
