package com.gestion.banking.services.impl;

import com.gestion.banking.configuration.JwtUtils;
import com.gestion.banking.dto.AccountDto;
import com.gestion.banking.dto.AuthenticationRequest;
import com.gestion.banking.dto.AuthenticationResponse;
import com.gestion.banking.dto.UserDto;
import com.gestion.banking.model.Role;
import com.gestion.banking.model.User;
import com.gestion.banking.repositories.RoleRepository;
import com.gestion.banking.repositories.UserRepository;
import com.gestion.banking.services.AccountService;
import com.gestion.banking.services.UserService;
import com.gestion.banking.validators.ObjectsValidator;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";
    private final UserRepository userRepository;
    private final ObjectsValidator validator;

    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, ObjectsValidator validator, AccountService accountService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public java.lang.String save(UserDto dto) {
        validator.validate(dto);
        User user=UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user).getId();
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(String id) {
        return userRepository.findById(id)
                .map(UserDto::toDto)
                .orElseThrow(()-> new EntityNotFoundException("No user was found with theprovided ID :" +id));
    }

    @Override
    @Transactional
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public String validateAccount(String id) {
        User user= userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user acount validation"));
        // create a bank acount
        AccountDto account = AccountDto.builder()
                .user(UserDto.toDto(user))
                .build();
        accountService.save(account);
        user.setActive(true);
        userRepository.saveAndFlush(user);
        return user.getId();
    }

    @Override
    @Transactional
    public String invalidateAccout(String id) {
        User user= userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user acount validation"));
        user.setActive(false);
        userRepository.saveAndFlush(user);
        return user.getId();
    }

    @Override
    @Transactional
    public AuthenticationResponse register(UserDto dto) {
        validator.validate(dto);
        User user=UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(findOrCreateRole(ROLE_USER));
        User userSaved = userRepository.saveAndFlush(user);
        Map<String, Object> claims= new HashMap<>();
        claims.put("id", userSaved.getId());
        claims.put("fullName", userSaved.getFirtName() +" "+ userSaved.getLastName());
        String token= jwtUtils.generateToken(userSaved, claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        final User user= userRepository.findByEmail(authenticationRequest.getEmail()).get();
        Map<String, Object> claims= new HashMap<>();
        claims.put("id", user.getId());
        claims.put("fullName", user.getFirtName() +" "+ user.getLastName());
        final String token = jwtUtils.generateToken(user, claims);
        return AuthenticationResponse.builder()
                        .token(token)
                        .build();
    }


    private Role findOrCreateRole(String roleName){
        Role role= roleRepository.findByName(roleName).orElse(null);
        if (role==null){
            return roleRepository.saveAndFlush(
                    Role.builder()
                            .name(roleName)
                            .build()
            );
        }
        return role;
    }
}
