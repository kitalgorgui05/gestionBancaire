package com.gestion.banking.controllers;

import com.gestion.banking.dto.UserDto;
import com.gestion.banking.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
@Tag(name = "user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<String> save(
            @RequestBody UserDto userDto
    ){
        return ResponseEntity.ok(userService.save(userDto));
    }
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(
            @PathVariable("id") String id
    ){
        return ResponseEntity.ok(userService.findById(id));
    }
    @PatchMapping("/validation/{id}")
     public ResponseEntity<String> validationAccount(@PathVariable("id") String id){
        return ResponseEntity.ok(userService.validateAccount(id));
     }

    @PatchMapping("/invalidation/{id}")
    public ResponseEntity<String> invalidationAccount(@PathVariable("id") String id){
        return ResponseEntity.ok(userService.invalidateAccout(id));
    }
@DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        userService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
