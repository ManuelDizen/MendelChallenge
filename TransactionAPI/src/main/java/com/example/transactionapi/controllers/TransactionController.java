package com.example.transactionapi.controllers;

import com.example.transactionapi.dtos.TransactionDTO;
import com.example.transactionapi.services.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/sum/{transaction_id}")
    public ResponseEntity<?> getTransactionSumById(@PathVariable Long transaction_id){
        Double sum = transactionService.getTransactionSumById(transaction_id);
        if(sum == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Collections.singletonMap("sum", sum));
    }
    
    @PutMapping("/{transaction_id}")
    public ResponseEntity<?> putTransaction(@PathVariable Long transaction_id,
                                         @RequestBody TransactionDTO transaction){
        transactionService.putTransaction(transaction_id, transaction.getAmount(),
                transaction.getType(), transaction.getParentId());
        return ResponseEntity.ok("{\"status\": \"ok\"}");
    }

    @GetMapping("/types/{type}")
    public ResponseEntity<?> getTransactionByType(@PathVariable String type){
        return ResponseEntity.ok(transactionService.getTransactionsByType(type));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(transactionService.getAll());
    }
}
