package com.example.transactionapi.services;

import com.example.transactionapi.models.Transaction;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {

    private final Map<Long, Transaction> transactions = new HashMap<>();
    private final Map<String, Set<Long>> types = new HashMap<>();


    public Double getTransactionSumById(Long id) {
        //TODO: Validate if id exists
        return -1.0;
    }

    public Set<Long> getTransactionsByType(String type){
        //TODO: Validate if type is key
        return types.get(type);
    }

    public void putTransaction(Long transaction_id, Double amount, String type, Long parent_id){

    }


}
