package com.example.transactionapi.interfaces.exceptions;

import java.util.NoSuchElementException;

public class NoSuchTransactionException extends NoSuchElementException {
    private final static String MESSAGE = "Transaction does not exist";

    public NoSuchTransactionException(){
        super(MESSAGE);
    }
}
