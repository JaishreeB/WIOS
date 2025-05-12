package com.cts.wios.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.wios.dto.Stock;
import com.cts.wios.dto.StockTransactionResponseDTO;
import com.cts.wios.dto.UserRole;
import com.cts.wios.dto.UserTransactionResponseDTO;
import com.cts.wios.exceptions.TransactionLogNotFound;
import com.cts.wios.feignclient.StockClient;
import com.cts.wios.feignclient.UserClient;
import com.cts.wios.model.TransactionLog;
import com.cts.wios.repository.TransactionLogRepository;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {
	@Autowired
	TransactionLogRepository repository;
	@Autowired
	StockClient stockClient;
	@Autowired
	UserClient userClient;

	@Override
	public String recordTransactionLog(TransactionLog transactionLog) {
		repository.save(transactionLog);
		return "Transaction saved successfully";
	}

	@Override
	public TransactionLog getTransactionLogById(int transactionId) throws TransactionLogNotFound {
		Optional<TransactionLog> optional = repository.findById(transactionId);
		if (optional.isPresent())
			return optional.get();
		else
			throw new TransactionLogNotFound("transaction log not found");
	}

	@Override
	public List<TransactionLog> getAllTransactionLogs() {
		return repository.findAll();
	}

	@Override
	public StockTransactionResponseDTO getTransactionLogsByStock(int stockId) {
		List<TransactionLog> transactions = repository.findByStockIdIs(stockId);
		Stock stock = stockClient.viewStock(stockId);
		StockTransactionResponseDTO response = new StockTransactionResponseDTO(stock,transactions);
		return response;

	}
	
	@Override
	public List<TransactionLog> getTransactionLogsByZone(int zoneId) {
		List<TransactionLog> transactions = repository.findByZoneIdIs(zoneId);
		return transactions;

	}

	@Override
	public List<TransactionLog> getTransactionLogsByPriceBetween(Double initialPrice, Double finalPrice) {
		return repository.findByPriceBetween(initialPrice, finalPrice);
	}

	@Override
	public List<TransactionLog> getTransactionLogsByType(String type) {
		return repository.findByTypeIs(type);
	}

	@Override
	public String deleteTransactionLog(int transactionId) {
		repository.deleteById(transactionId);
		return "Transaction deleted successfully";
	}

	@Override
	public List<TransactionLog> getTransactionLogsByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate) {
		return repository.findByTimestampBetween(startDate, endDate);
	}

	@Override
	public UserTransactionResponseDTO getTransactionLogsByUser(int userId) {
		List<TransactionLog> transaction = repository.findByStockIdIs(userId);
		UserRole user = userClient.viewTransactionByUser(userId);
		UserTransactionResponseDTO responseDTO = new UserTransactionResponseDTO(user, transaction);
		return responseDTO;
	}

}
