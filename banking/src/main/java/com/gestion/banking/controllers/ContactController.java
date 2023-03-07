package com.gestion.banking.controllers;

import com.gestion.banking.dto.ContactDto;
import com.gestion.banking.services.ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin("*")
@Tag(name = "contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/")
    public ResponseEntity<String> save(@RequestBody ContactDto contactDto){
        return ResponseEntity.ok(contactService.save(contactDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<ContactDto>> findAll(){
        return ResponseEntity.ok(contactService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<ContactDto>> findAllByUserId(
            @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(contactService.findAllByUserId(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> fingById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(contactService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id
    ) {
        contactService.delete(id);
        return ResponseEntity.accepted().build();
    }

}
