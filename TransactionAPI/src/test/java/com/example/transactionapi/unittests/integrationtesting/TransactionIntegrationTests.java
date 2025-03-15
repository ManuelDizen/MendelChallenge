package com.example.transactionapi.unittests.integrationtesting;

import com.example.transactionapi.interfaces.TransactionRepository;
import com.example.transactionapi.interfaces.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionIntegrationTests {

    /*
    Casos posibles:

        - Para el PUT
            - Crear transacción sin parent_id
            - Crear transacción con parent_id
            - Crear transacción con parent_id invalido
        - Para el GET types
            - Un type uqe no tenga ninguna trans
            - Un type que existe
        - Para el GET suma
            - No existe ID de trans
            - Suma con ciclo que termine bien
            - Suma
     */

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void putValidTransactionSuccess() throws Exception{
        mockMvc.perform(put("/transactions/1")
                        .contentType("application/json")
                        .content("{\"amount\": 100.0, \"type\": \"shopping\", \"parentId\": null}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\": \"ok\"}"));
    }

    @Test
    void putValidTransactionNoParentIdSuccess() throws Exception{
        mockMvc.perform(put("/transactions/1")
                        .contentType("application/json")
                        .content("{\"amount\": 100.0, \"type\": \"shopping\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\": \"ok\"}"));
    }

    @Test
    void putInvalidParentIdSuccess() throws Exception{
        // Invalid parent id allows to create transaction but parent_id = null
        mockMvc.perform(put("/transactions/1")
                        .contentType("application/json")
                        .content("{\"amount\": 100.0, \"type\": \"shopping\", \"parentId\": 2}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\": \"ok\"}"));
    }


    @Test
    void sumTransactionWithoutLoopSuccess() throws Exception{
        transactionRepository.createTransaction(1L, 100.0, "shopping", null);
        transactionRepository.createTransaction(2L, 50.0, "shopping", 1L);

        MvcResult result = mockMvc.perform(get("/transactions/sum/1"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).contains("\"sum\":150.0");
    }

    @Test
    void sumTransactionWithLoopSuccess() throws Exception{
        transactionRepository.createTransaction(1L, 100.0, "shopping", 3L);
        transactionRepository.createTransaction(2L, 50.0, "shopping", 1L);
        transactionRepository.createTransaction(3L, 100.0, "shopping", 2L);

        MvcResult result = mockMvc.perform(get("/transactions/sum/1"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).contains("\"sum\":250.0");
    }

    @Test
    void sumTransactionNonExistingFailure() throws Exception{
        mockMvc.perform(get("/transactions/sum/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTypesExistingSuccess() throws Exception{
        transactionRepository.createTransaction(1L, 100.0, "shopping", null);
        transactionRepository.createTransaction(2L, 50.0, "shopping", null);

        MvcResult result = mockMvc.perform(get("/transactions/types/shopping"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).contains("1");
        assertThat(responseBody).contains("2");
    }

    @Test
    void getNonExistingTypeReturnsEmpty() throws Exception{
        String nonExistingType = "nonExistingType";

        MvcResult result = mockMvc.perform(get("/transactions/types/" + nonExistingType))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isEqualTo("[]");
    }

}
