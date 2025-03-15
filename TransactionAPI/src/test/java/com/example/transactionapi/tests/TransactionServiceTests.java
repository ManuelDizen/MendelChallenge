package com.example.transactionapi.tests;

import com.example.transactionapi.interfaces.TransactionRepository;
import com.example.transactionapi.interfaces.TransactionService;
import com.example.transactionapi.models.Transaction;

import com.example.transactionapi.repositories.TransactionRepositoryImpl;
import com.example.transactionapi.services.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTests {
        @InjectMocks
        private TransactionServiceImpl transactionService;

        @Mock
        private TransactionRepositoryImpl transactionRepository;

    @Test
    void shouldUpdateExistingTransactionWithoutTypeChange() {
        Long transactionId = 1L;
        Double newAmount = 2000.0;
        String type = "shopping";

        Transaction existingTransaction = new Transaction(transactionId, 1000.0, type, null);
        when(transactionRepository.transactionExists(transactionId)).thenReturn(true);
        when(transactionRepository.getById(transactionId)).thenReturn(existingTransaction);

        transactionService.putTransaction(transactionId, newAmount, type, null);

        verify(transactionRepository, never()).updateType(anyLong(), anyString(), anyString());
        verify(transactionRepository).updateTransaction(transactionId, newAmount, type, null);
    }

    @Test
    void shouldUpdateTransactionAndChangeType() {
        Long transactionId = 1L;
        Double newAmount = 3000.0;
        String oldType = "cars";
        String newType = "shopping";

        Transaction existingTransaction = new Transaction(transactionId, 1000.0, oldType, null);
        when(transactionRepository.transactionExists(transactionId)).thenReturn(true);
        when(transactionRepository.getById(transactionId)).thenReturn(existingTransaction);

        transactionService.putTransaction(transactionId, newAmount, newType, null);

        verify(transactionRepository).updateType(transactionId, oldType, newType);
        verify(transactionRepository).updateTransaction(transactionId, newAmount, newType, null);
    }

    @Test
    void shouldCreateNewTransactionIfNotExists() {
        Long transactionId = 2L;
        Double amount = 5000.0;
        String type = "electronics";

        when(transactionRepository.transactionExists(transactionId)).thenReturn(false);

        transactionService.putTransaction(transactionId, amount, type, null);

        verify(transactionRepository).createTransaction(transactionId, amount, type, null);
    }

    @Test
    void shouldHandleParentIdWhenCreatingNewTransaction() {
        Long transactionId = 3L;
        Double amount = 7000.0;
        String type = "home";
        Long parentId = 1L;

        when(transactionRepository.transactionExists(transactionId)).thenReturn(false);
        when(transactionRepository.transactionExists(parentId)).thenReturn(true);

        transactionService.putTransaction(transactionId, amount, type, parentId);

        verify(transactionRepository).createTransaction(transactionId, amount, type, parentId);
    }

    @Test
    void shouldReturnTransactionIdsByType() {
        when(transactionRepository.getByType("shopping")).thenReturn(Set.of(11L, 12L));

        Set<Long> result = transactionService.getTransactionsByType("shopping");

        assertEquals(Set.of(11L, 12L), result);
    }

    @Test
    void shouldCalculateSumCorrectly() {
        Transaction t1 = new Transaction(10L, 5000.0, "shopping", null);
        Transaction t2 = new Transaction(11L, 10000.0, "shopping", 10L);
        Transaction t3 = new Transaction(12L, 5000.0, "shopping", 11L);

        when(transactionRepository.getById(10L)).thenReturn(t1);
        when(transactionRepository.getById(11L)).thenReturn(t2);
        t1.addChildTransaction(11L);
        when(transactionRepository.getById(12L)).thenReturn(t3);
        t2.addChildTransaction(12L);

        when(transactionRepository.transactionExists(10L)).thenReturn(true);

        double sum = transactionService.getTransactionSumById(10L);
        assertEquals(20000.0, sum);
    }
}
