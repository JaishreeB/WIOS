package com.cts.wios.service;

import java.time.LocalDate;
import java.util.List;

import com.cts.wios.model.TransactionLog;

public interface TransactionLogService {
	public String recordTransactionLog(TransactionLog transactionLog);

	public TransactionLog getTransactionLogById(int transactionId);
	
	public List<TransactionLog> getAllTransactionLogs();

	public String deleteTransactionLog(int transactionId);
	
//	public List<TransactionLog> getTransactionLogsByZone(int zoneId);
	
	public List<TransactionLog> getTransactionLogsByTimestampBetween(LocalDate startDate,LocalDate endDate);

	public List<TransactionLog> getTransactionLogsByPriceBetween(Double initialPrice,Double finalPrice);
	
	public List<TransactionLog> getTransactionLogsByType(String type);

	List<TransactionLog> getTransactionLogsByStock(int stockId);

	List<TransactionLog> getTransactionLogsByVendor(int vendorId);
}
