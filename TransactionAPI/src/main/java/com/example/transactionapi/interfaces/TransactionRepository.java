package com.example.transactionapi.interfaces;

import com.example.transactionapi.models.Transaction;

import java.util.Set;

public interface TransactionRepository {
    public Boolean transactionExists(Long id);
    public Set<Long> getByType(String type);
    public Transaction getById(Long id);
    public void updateType(Long id, String oldType, String newType);
    public void updateTransaction(Long transaction_id, Double amount, String type, Long parent_id);

    public void createTransaction(Long transaction_id, Double amount, String type, Long parent_id);

    public Set<Transaction> getAll();
}
