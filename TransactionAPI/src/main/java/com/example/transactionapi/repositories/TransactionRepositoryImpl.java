package com.example.transactionapi.repositories;

import com.example.transactionapi.models.Transaction;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class TransactionRepository {

    private final Map<Long, Transaction> transactions = new HashMap<>();
    private final Map<String, Set<Long>> types = new HashMap<>();


}
