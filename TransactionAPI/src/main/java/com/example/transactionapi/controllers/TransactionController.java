package com.example.transactionapi.controllers;

import com.example.transactionapi.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/sum/{transaction_id}")
    public ResponseEntity getTransactionSumById(@PathVariable Long transaction_id){
        Double sum = transactionService.getTransactionSumById(transaction_id);
        return ResponseEntity.ok(Collections.singletonMap("sum", sum));
    }
    
    @PutMapping("/transactions/{transaction_id}")
    public ResponseEntity createTransaction(@PathVariable Long transaction_id){
        return ResponseEntity.ok(0);
    }

    @GetMapping("/types/{type}")
    public ResponseEntity getTransactionByType(@PathVariable String type){
        return ResponseEntity.ok(0);
    }
}
