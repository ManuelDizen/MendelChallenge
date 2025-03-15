package com.example.transactionapi.interfaces;

import com.example.transactionapi.models.Transaction;

import java.util.Set;

public interface TransactionRepository {
    Boolean transactionExists(Long id);
    Set<Long> getByType(String type);
    Transaction getById(Long id);
    void updateType(Long id, String oldType, String newType);
    void updateTransaction(Long transaction_id, Double amount, String type, Long parent_id);

    void createTransaction(Long transaction_id, Double amount, String type, Long parent_id);

    Set<Transaction> getAll();
}
