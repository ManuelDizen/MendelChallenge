package com.example.transactionapi.repositories;

import com.example.transactionapi.interfaces.TransactionRepository;
import com.example.transactionapi.models.Transaction;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final Map<Long, Transaction> transactions = new HashMap<>();
    private final Map<String, Set<Long>> types = new HashMap<>();

    @Override
    public Boolean transactionExists(Long id) {
        return transactions.containsKey(id);
    }

    @Override
    public Set<Long> getByType(String type) {
        if(!types.containsKey(type)){
            return new HashSet<>();
        }
        return types.get(type);
    }

    @Override
    public Transaction getById(Long id) {
        return transactions.get(id);
    }

    @Override
    public void updateType(Long id, String oldType, String newType) {
        types.get(oldType).remove(id);
        if(types.get(oldType).isEmpty()){
            types.remove(oldType);
        }
        addIdToType(id, newType);
    }

    public void addIdToType(Long id, String type){
        if(!types.containsKey(type)) {
            types.put(type, new HashSet<>());
        }
        types.get(type).add(id);
    }

    @Override
    public void updateTransaction(Long transaction_id, Double amount, String type, Long parent_id) {
        Transaction transaction = getById(transaction_id);
        transaction.setAmount(amount);
        transaction.setType(type);
        updateParents(transaction_id, transaction.getParent_id(), parent_id);
        transaction.setParent_id(parent_id);
    }

    private void updateParents(Long transaction_id, Long oldParentId, Long newParentId) {
        if (oldParentId != null) {
            transactions.get(oldParentId).removeChildTransaction(transaction_id);
        }
        if(newParentId != null) addChildTransaction(transaction_id, newParentId);
    }

    private void addChildTransaction(Long transaction_id, Long parentId){
        transactions.get(parentId).addChildTransaction(transaction_id);
    }

    @Override
    public void createTransaction(Long transaction_id, Double amount, String type, Long parent_id) {
        if(!transactions.containsKey(parent_id)) parent_id = null;
        transactions.put(transaction_id, new Transaction(transaction_id, amount, type, parent_id));
        if(parent_id != null){
            transactions.get(parent_id).addChildTransaction(transaction_id);
        }
        addIdToType(transaction_id, type);
    }

    public Set<Transaction> getAll(){
        return new HashSet<>(transactions.values());
    }
}
