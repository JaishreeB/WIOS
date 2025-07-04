package com.cts.wios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.wios.dto.StockTransactionResponseDTO;
import com.cts.wios.exceptions.TransactionLogNotFound;
import com.cts.wios.model.TransactionLog;
import com.cts.wios.repository.TransactionLogRepository;
import com.cts.wios.service.TransactionLogServiceImpl;

@SpringBootTest
class TransactionLogManagementApplicationTests {
	@Mock
	TransactionLogRepository repository;

	@InjectMocks
	TransactionLogServiceImpl service;

	@Test
	void recordTransactionLogTest() {
		{
			TransactionLog transactionLog = new TransactionLog(2, 1, 1, 1, 10, "Inbound", 20000);
			Mockito.when(repository.save(transactionLog)).thenReturn(transactionLog);
			String response = service.recordTransactionLog(transactionLog);
			assertEquals("Transaction saved successfully", response);
		}
	}

	@Test
	void getTransactionLogByIdTest() throws TransactionLogNotFound {
		int transactionId = 2;
		TransactionLog transactionLog = new TransactionLog(2, 1, 1, 1, 10, "Inbound", 20000);
		Mockito.when(repository.findById(transactionId)).thenReturn(Optional.of(transactionLog));
		TransactionLog response = service.getTransactionLogById(transactionId);
		assertEquals(transactionLog, response);

	}

	@Test
	void getAllTransactionLogsTest() {
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 20000),
				new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findAll()).thenReturn(transactionLogs);
		List<TransactionLog> response = service.getAllTransactionLogs();
		assertEquals(transactionLogs, response);
	}

//	@Test
//	void deleteTransactionLogTest() {
//		int transactionId = 10;
//		Mockito.doNothing().when(repository).deleteById(transactionId);
//		String response = service.deleteTransactionLog(transactionId);
//		assertEquals("Transaction deleted successfully", response);
//	}

	@Test
	void getTransactionLogsByPriceBetweenTest() {
		Double initialPrice = 1000.00;
		Double finalPrice = 25000.00;
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 1500),
				new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findByPriceBetween(initialPrice, finalPrice)).thenReturn(transactionLogs);
		List<TransactionLog> response = service.getTransactionLogsByPriceBetween(initialPrice, finalPrice);
		assertEquals(transactionLogs, response);
	}

	@Test
	void getTransactionLogsByTypeTest() {
		String type = "Inbound";
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 1500),
				new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findByTypeIs(type)).thenReturn(transactionLogs);
		List<TransactionLog> response = service.getTransactionLogsByType(type);
		assertEquals(transactionLogs, response);

	}

	@Test
	void getTransactionLogsByStockTest() {
		int stockId = 1;
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 1500),
				new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findByStockIdIs(stockId)).thenReturn(transactionLogs);
		StockTransactionResponseDTO response = service.getTransactionLogsByStock(stockId);
		assertEquals(transactionLogs, response);

	}

	@Test
	void getTransactionLogsByVendorTest() {

		int vendorId = 1;
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 1500),
				new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findByStockIdIs(vendorId)).thenReturn(transactionLogs);
		StockTransactionResponseDTO response = service.getTransactionLogsByStock(vendorId);
		assertEquals(transactionLogs, response);
	}

}
