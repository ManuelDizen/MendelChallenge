package com.example.transactionapi.handlers;

import com.example.transactionapi.interfaces.exceptions.NoSuchTransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchTransactionException.class)
    public ResponseEntity<String> transactionNotFoundHandler(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
