package com.cts.wios.controller;

import java.time.LocalDate;
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
	public TransactionLog getTransactionLogById(@PathVariable("id") int transactionLog) {
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
	public List<TransactionLog> getAllStocks() {
		return service.getAllTransactionLogs();
	}

	@GetMapping("/fetchByStock/{stock}")
	public List<TransactionLog> getTransactionLogsByZone(@PathVariable("stock") int stockId) {
		return service.getTransactionLogsByStock(stockId);

	}

	@GetMapping("/fetchByVendor/{vendor}")
	public List<TransactionLog> getTransactionLogsByVendor(@PathVariable("vendor") int vendorId) {
		return service.getTransactionLogsByVendor(vendorId);

	}

	
	@GetMapping("/fetchByTimestamp/{start}/{end}")
	public List<TransactionLog> getTransactionLogsByTimestampBetween(@PathVariable("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate startDate,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @PathVariable("end") LocalDate endDate) {
		return service.getTransactionLogsByTimestampBetween(startDate, endDate);
	}

	@GetMapping("/fetchByPrice/{initial}/{final}")
	public List<TransactionLog> getTransactionLogsByPriceBetween(@PathVariable("initial") Double initialPrice,
			@PathVariable("final") Double finalPrice) {
		return service.getTransactionLogsByPriceBetween(initialPrice, finalPrice);

	}

}
