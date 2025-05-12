package com.cts.wios.service;

import java.time.LocalDateTime;
import java.util.List;

import com.cts.wios.dto.StockTransactionResponseDTO;
import com.cts.wios.dto.UserTransactionResponseDTO;
import com.cts.wios.exceptions.TransactionLogNotFound;
import com.cts.wios.model.TransactionLog;

public interface TransactionLogService {
	public String recordTransactionLog(TransactionLog transactionLog);

	public TransactionLog getTransactionLogById(int transactionId) throws TransactionLogNotFound;
	
	public List<TransactionLog> getAllTransactionLogs();

	public String deleteTransactionLog(int transactionId);

	public List<TransactionLog> getTransactionLogsByPriceBetween(Double initialPrice,Double finalPrice);
	
	public List<TransactionLog> getTransactionLogsByType(String type);

	StockTransactionResponseDTO getTransactionLogsByStock(int stockId);

	public List<TransactionLog> getTransactionLogsByTimestampBetween(LocalDateTime startDateTime,
			LocalDateTime endDateTime);

	public UserTransactionResponseDTO getTransactionLogsByUser(int userId);

	List<TransactionLog> getTransactionLogsByZone(int zoneId);
}
