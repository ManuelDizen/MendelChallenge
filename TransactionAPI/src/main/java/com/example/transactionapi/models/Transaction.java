package com.example.transactionapi.models;

import java.util.HashSet;
import java.util.Set;

public class Transaction {

    private Long id;
    private Double amount;
    private String type;
    private Long parent_id;
    private final Set<Long> child_transactions = new HashSet<>();

    public Transaction(Long id, Double amount, String type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    public Transaction(Long id, Double amount, String type, Long parent_id) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.parent_id = parent_id;
    }

    public void addChildTransaction(Long child_id){
        child_transactions.add(child_id);
    }

    public void removeChildTransaction(Long child_id){
        child_transactions.remove(child_id);
    }

    public Set<Long> getChildTransactions(){
        return this.child_transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }
}
