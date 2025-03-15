package com.example.transactionapi.tests;

import com.example.transactionapi.controllers.TransactionController;
import com.example.transactionapi.services.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TransactionControllerTests {

	@Mock
	private TransactionServiceImpl transactionServiceImpl;

	@InjectMocks
	private TransactionController transactionController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup(){
		mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
	}

	@Test
	void shouldCreateTransactionSuccessfully() throws Exception {
		String requestBody = """
            {
                "amount": 5000,
                "type": "cars"
            }
        """;

		mockMvc.perform(put("/transactions/10")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("ok"));
	}

	@Test
	void shouldReturnTransactionsByType() throws Exception {
		when(transactionServiceImpl.getTransactionsByType("shopping")).thenReturn(Set.of(11L, 12L));

		mockMvc.perform(get("/transactions/types/shopping"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").value(11))
				.andExpect(jsonPath("$[1]").value(12));
	}

	@Test
	void shouldReturnTransactionSum() throws Exception {
		when(transactionServiceImpl.getTransactionSumById(10L)).thenReturn(20000.0);

		mockMvc.perform(get("/transactions/sum/10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sum").value(20000.0));
	}

}
