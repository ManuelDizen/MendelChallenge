package com.example.transactionapi.dtos;
public class TransactionDTO {
    private double amount;
    private String type;
    private Long parent_id;

    public TransactionDTO(double amount, String type, Long parent_id) {
        this.amount = amount;
        this.type = type;
        this.parent_id = parent_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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