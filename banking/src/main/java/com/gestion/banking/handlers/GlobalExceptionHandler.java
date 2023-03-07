package com.gestion.banking.handlers;

import com.gestion.banking.exceptions.ObjectValidationException;
import com.gestion.banking.exceptions.OperationNotPermitteException;
import com.gestion.banking.validators.ObjectsValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ObjectValidationException.class)
    public ResponseEntity<ExceptionRepresentation> handlerException(ObjectValidationException objectValidationException){
        ExceptionRepresentation exceptionRepresentation = ExceptionRepresentation.builder()
                .errorMessage("Object Not valid exception has occured")
                .errorSource(objectValidationException.getViolationSource())
                .validatorsError(objectValidationException.getValidations())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionRepresentation);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(EntityNotFoundException e){
        ExceptionRepresentation exceptionRepresentation= ExceptionRepresentation.builder()
                .errorMessage(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionRepresentation);
    }

    @ExceptionHandler(OperationNotPermitteException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(OperationNotPermitteException e){
        ExceptionRepresentation exceptionRepresentation= ExceptionRepresentation.builder()
                .errorMessage(e.getErrorMsg())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(exceptionRepresentation);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(){
        ExceptionRepresentation exceptionRepresentation= ExceptionRepresentation.builder()
                .errorMessage("A user already exists with the provided Email")
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionRepresentation);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionRepresentation> handleDisabledException(){
        ExceptionRepresentation exceptionRepresentation= ExceptionRepresentation.builder()
                .errorMessage("You cannot access your acount because it is not yet activated")
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exceptionRepresentation);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionRepresentation> handleBadCredentialsException(){
        ExceptionRepresentation exceptionRepresentation= ExceptionRepresentation.builder()
                .errorMessage("Your email and / or password is incorrect")
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exceptionRepresentation);
    }
}
