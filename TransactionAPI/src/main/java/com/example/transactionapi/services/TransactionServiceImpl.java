package com.example.transactionapi.services;

import com.example.transactionapi.interfaces.TransactionRepository;
import com.example.transactionapi.interfaces.TransactionService;
import com.example.transactionapi.interfaces.exceptions.NoSuchTransactionException;
import com.example.transactionapi.models.Transaction;
import com.example.transactionapi.repositories.TransactionRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepositoryImpl transactionRepositoryImpl) {
        this.transactionRepository = transactionRepositoryImpl;
    }

    public Set<Transaction> getAll(){
        return transactionRepository.getAll();
    }

    public Double getTransactionSumById(Long id) {
        if(!transactionRepository.transactionExists(id)){
            throw new NoSuchTransactionException();
        }

        Stack<Long> toProcess = new Stack<>();
        Set<Long> processed = new HashSet<>();
        Double sum = 0.0;
        toProcess.push(id);
        while(!toProcess.isEmpty()){
            Long current = toProcess.pop();
            if(!processed.contains(current)){
                processed.add(current);
                Transaction transaction = transactionRepository.getById(current);

                Set<Long> linked = transaction.getChildTransactions();
                toProcess.addAll(linked);
                sum += transaction.getAmount();
            }
        }

        return sum;
    }

    public Set<Long> getTransactionsByType(String type){
        return transactionRepository.getByType(type);
    }

    public void putTransaction(Long transaction_id, Double amount, String type, Long parent_id){
        if(transactionRepository.transactionExists(transaction_id)){
            Transaction transaction = transactionRepository.getById(transaction_id);
            if(!Objects.equals(transaction.getType(), type)){
                transactionRepository.updateType(transaction_id, transaction.getType(), type);
            }
            transactionRepository.updateTransaction(transaction_id, amount, type, parent_id);
        }
        else{
            transactionRepository.createTransaction(transaction_id, amount, type, parent_id);
        }
    }


}
