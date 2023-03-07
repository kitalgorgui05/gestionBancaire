package com.gestion.banking.services;

import com.gestion.banking.dto.AuthenticationRequest;
import com.gestion.banking.dto.AuthenticationResponse;
import com.gestion.banking.dto.UserDto;

public interface UserService extends AbstractService<UserDto>{
    String validateAccount(String id);
    String invalidateAccout(String id);

    AuthenticationResponse register(UserDto userDto);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
