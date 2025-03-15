package com.example.transactionapi.interfaces;

import java.util.Set;

public interface TransactionService {

    Double getTransactionSumById(Long id);
    Set<Long> getTransactionsByType(String type);
    void putTransaction(Long transaction_id, Double amount, String type, Long parent_id);
}
