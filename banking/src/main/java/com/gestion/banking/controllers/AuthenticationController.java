package com.gestion.banking.controllers;

import com.gestion.banking.configuration.JwtUtils;
import com.gestion.banking.dto.AuthenticationRequest;
import com.gestion.banking.dto.AuthenticationResponse;
import com.gestion.banking.dto.UserDto;
import com.gestion.banking.repositories.UserRepository;
import com.gestion.banking.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Tag(name = "authentication")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserDto userDto
    ){
        return ResponseEntity.ok(userService.register(userDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ){
        return ResponseEntity.ok(userService.authenticate(authenticationRequest));
    }
}
