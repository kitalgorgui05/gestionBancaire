package com.gestion.banking.controllers;

import com.gestion.banking.dto.AddressDto;
import com.gestion.banking.services.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import java.util.List;

@RestController
@RequestMapping("/address")
@CrossOrigin("*")
@Tag(name = "address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> save(
            @RequestBody AddressDto addressDto
    ){
        return ResponseEntity.ok(addressService.save(addressDto));
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<AddressDto>> findAll(){
        return ResponseEntity.ok(addressService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> findById(
            @PathVariable("id") String id
    ){
        return ResponseEntity.ok(addressService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        addressService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
