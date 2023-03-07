package com.gestion.banking.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class ObjectValidationException extends RuntimeException{
    @Getter
    private final Set<String> validations;
    @Getter
    private final String violationSource;
}
