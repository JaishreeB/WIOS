package com.cts.wios.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.wios.dto.StockTransactionResponseDTO;
import com.cts.wios.dto.UserTransactionResponseDTO;
import com.cts.wios.exceptions.TransactionLogNotFound;
import com.cts.wios.model.TransactionLog;
import com.cts.wios.service.TransactionLogService;

@RestController
@RequestMapping("/transactionLog")
public class TransactionLogController {
	@Autowired
	TransactionLogService service;

	@PostMapping("/save")
	public String recordTransactionLog(@RequestBody TransactionLog transactionLog) {
		return service.recordTransactionLog(transactionLog);
	}

	@GetMapping("/fetchById/{id}")
	public TransactionLog getTransactionLogById(@PathVariable("id") int transactionLog) throws TransactionLogNotFound {
		return service.getTransactionLogById(transactionLog);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteTransactionLog(@PathVariable("id") int transactionLog) {
		return service.deleteTransactionLog(transactionLog);

	}

	@GetMapping("/fetchByType/{TransactionType}")
	public List<TransactionLog> getTransactionLogsByType(@PathVariable("TransactionType") String type) {
		return service.getTransactionLogsByType(type);
	}

	@GetMapping("/fetchAll")
	public List<TransactionLog> getAllTransactionLogs() {
		return service.getAllTransactionLogs();
	}

	@GetMapping("/fetchByStock/{stock}")
	public StockTransactionResponseDTO getTransactionLogsByStock(@PathVariable("stock") int stockId) {
		return service.getTransactionLogsByStock(stockId);
	}
	
	@GetMapping("/fetchByZone/{zone}")
	public List<TransactionLog> getTransactionLogsByZone(@PathVariable("zone") int zoneId) {
		return service.getTransactionLogsByZone(zoneId);

	}


	@GetMapping("/fetchByUser/{user}")
	public UserTransactionResponseDTO getTransactionLogsByUser(@PathVariable("user") int userId) {
		return service.getTransactionLogsByUser(userId);

	}

	
	@GetMapping("/fetchByDateTimestamp/{start}/{end}")
	public List<TransactionLog> getTransactionLogsByDateTimestampBetween(@PathVariable("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate startDate,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @PathVariable("end") LocalDate endDate) {

		// Convert LocalDate to LocalDateTime
		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
		return service.getTransactionLogsByTimestampBetween(startDateTime, endDateTime);
	}

	@GetMapping("/fetchByTimestamp/{start}/{end}")//http://localhost:9090/transactionlog/fetchByTimestamp/{start}/{end}
	public List<TransactionLog> findByTimestampBetween(@PathVariable("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime startDate,
			 @PathVariable("end") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
		return service.getTransactionLogsByTimestampBetween(startDate, endDate);
	}
 
	@GetMapping("/fetchByPrice/{initial}/{final}")
	public List<TransactionLog> getTransactionLogsByPriceBetween(@PathVariable("initial") Double initialPrice,
			@PathVariable("final") Double finalPrice) {
		return service.getTransactionLogsByPriceBetween(initialPrice, finalPrice);
	}

}
