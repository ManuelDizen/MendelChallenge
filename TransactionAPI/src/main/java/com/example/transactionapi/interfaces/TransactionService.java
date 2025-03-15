package com.example.transactionapi.interfaces;

import java.util.Set;

public interface TransactionService {

    public Double getTransactionSumById(Long id);
    public Set<Long> getTransactionsByType(String type);
    public void putTransaction(Long transaction_id, Double amount, String type, Long parent_id);
}
