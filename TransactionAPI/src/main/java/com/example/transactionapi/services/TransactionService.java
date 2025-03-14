package com.example.transactionapi.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    public Double getTransactionSumById(Long id) {
        return -1.0;
    }

    public List<Long> getTransactionsByType(String type){
        return new ArrayList<>();
    }


}
