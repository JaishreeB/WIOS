package com.cts.wios.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.wios.model.TransactionLog;
import com.cts.wios.repository.TransactionLogRepository;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {
	@Autowired
	TransactionLogRepository repository;

	@Override
	public String recordTransactionLog(TransactionLog transactionLog) {
		repository.save(transactionLog);
		return "Transaction saved successfully";
	}

	@Override
	public TransactionLog getTransactionLogById(int transactionId) {
		Optional<TransactionLog> optional = repository.findById(transactionId);
		return optional.get();
	}

	@Override
	public List<TransactionLog> getAllTransactionLogs() {
		return repository.findAll();
	}

	@Override
	public List<TransactionLog> getTransactionLogsByStock(int stockId) {
		return repository.findByStockIdIs(stockId);
	}

	@Override
	public List<TransactionLog> getTransactionLogsByVendor(int vendorId) {
		return repository.findByVendorIdIs(vendorId);
	}

	@Override
	public List<TransactionLog> getTransactionLogsByTimestampBetween(LocalDate startDate, LocalDate endDate) {
		return repository.findByTimestampBetween(startDate,endDate);
	}

	@Override
	public List<TransactionLog> getTransactionLogsByPriceBetween(Double initialPrice, Double finalPrice) {
		return repository.findByPriceBetween(initialPrice,finalPrice);
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

}
//LocalDateTime dateTime = LocalDateTime.parse(timestamp);
//
//LocalDate date = dateTime.toLocalDate();
//LocalTime time = dateTime.toLocalTime();