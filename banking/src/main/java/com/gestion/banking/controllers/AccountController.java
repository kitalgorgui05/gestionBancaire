package com.gestion.banking.controllers;

import com.gestion.banking.dto.AccountDto;
import com.gestion.banking.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin("*")
@Tag(name = "account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/")
    public ResponseEntity<String> save(
            @RequestBody AccountDto accountDto
    ) {
        return ResponseEntity.ok(accountService.save(accountDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDto>> findAll(){
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findById(
            @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        accountService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
