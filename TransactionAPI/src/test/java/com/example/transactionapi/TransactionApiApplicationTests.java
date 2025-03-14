package com.example.transactionapi;

import com.example.transactionapi.controllers.TransactionController;
import com.example.transactionapi.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TransactionApiApplicationTests {

	@Mock
	private TransactionService transactionService;

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
		when(transactionService.getTransactionsByType("shopping")).thenReturn(List.of(11L, 12L));

		mockMvc.perform(get("/transactions/types/shopping"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").value(11))
				.andExpect(jsonPath("$[1]").value(12));
	}

	@Test
	void shouldReturnTransactionSum() throws Exception {
		when(transactionService.getTransactionSumById(10L)).thenReturn(20000.0);

		mockMvc.perform(get("/transactions/sum/10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sum").value(20000.0));
	}

}
