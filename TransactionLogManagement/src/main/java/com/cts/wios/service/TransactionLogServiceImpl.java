package com.cts.wios.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.wios.dto.Stock;
import com.cts.wios.dto.StockTransactionResponseDTO;
import com.cts.wios.dto.UserRole;
import com.cts.wios.dto.UserTransactionResponseDTO;
import com.cts.wios.exceptions.StockNotFoundException;
import com.cts.wios.exceptions.TransactionLogNotFound;
import com.cts.wios.feignclient.StockClient;
import com.cts.wios.feignclient.UserClient;
import com.cts.wios.model.TransactionLog;
import com.cts.wios.repository.TransactionLogRepository;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

	public TransactionLogServiceImpl(TransactionLogRepository repository, StockClient stockClient,
			UserClient userClient) {
		super();
		this.repository = repository;
		this.stockClient = stockClient;
		this.userClient = userClient;
	}
	@Autowired
	TransactionLogRepository repository;
	@Autowired
	StockClient stockClient;
	@Autowired
	UserClient userClient;

	private static final Logger logger = LoggerFactory.getLogger(TransactionLogServiceImpl.class);

	@Override
	public String recordTransactionLog(TransactionLog transactionLog) {
		int stockId = transactionLog.getStockId();
		Stock stock = stockClient.viewStock(stockId);
		transactionLog.setStockName(stock.getStockName());
		if (stock == null) {
			logger.error("Stock item not found with ID: {}", stockId);
			throw new StockNotFoundException("StockItem Not Found");
		}
		logger.info("Recording transaction log: {}", transactionLog);
		repository.save(transactionLog);
		logger.info("Transaction log saved successfully: {}", transactionLog);
		updateStockBasedOnTransaction(transactionLog);
		return "Transaction Saved and Stock Updated!!!";
	}

	private void updateStockBasedOnTransaction(TransactionLog transactionLog) throws StockNotFoundException {
		logger.info("Updating stock item based on transaction: {}", transactionLog);
		int stockId = transactionLog.getStockId();
		Stock stock = stockClient.viewStock(stockId);

		if (stock == null) {
			logger.error("Stock item not found with ID: {}", stockId);
			throw new StockNotFoundException("StockItem Not Found");
		}

		if (transactionLog.getType().equalsIgnoreCase("inbound")) {
			logger.info("Processing inbound transaction for stock item: {}", stock);
			
			stock.setStockQuantity(stock.getStockQuantity() + transactionLog.getQuantity());
			stockClient.updateStockForInbound(stock);
//			stockClient.createStock(stock);
			logger.info("Stock item updated successfully for outbound transaction: {}", stock);
		} else if (transactionLog.getType().equalsIgnoreCase("outbound")) {
			logger.info("Processing outbound transaction for stock item: {}", stock);
			stock.setStockQuantity(stock.getStockQuantity() - transactionLog.getQuantity());
			stockClient.updateStockForOutbound(stock);
			//UPDATING
//			stockClient.createStock(stock);
			logger.info("Stock item updated successfully for outbound transaction: {}", stock);
		} else {
			logger.error("Invalid transaction type: {}", transactionLog.getType());
			throw new IllegalArgumentException("Invalid transaction type");
		}
	}

	@Override
	public TransactionLog getTransactionLogById(int transactionId) throws TransactionLogNotFound {
		logger.info("Retrieving transaction log with ID: {}", transactionId);
		Optional<TransactionLog> optional = repository.findById(transactionId);
		if (optional.isPresent()) {
			logger.info("Transaction log found: {}", optional.get());
			return optional.get();
		}

		else {
			logger.error("Transaction log not found with ID: {}", transactionId);
			throw new TransactionLogNotFound("transaction log not found");
		}
	}

	@Override
	public List<TransactionLog> getAllTransactionLogs() {
		logger.info("Retrieving all transaction logs");
		List<TransactionLog> transactionLogs = repository.findAll();
		logger.info("All transaction logs retrieved successfully");
		return transactionLogs;
	}

	@Override
	public StockTransactionResponseDTO getTransactionLogsByStock(int stockId) {
		logger.info("Retrieving transaction logs for stock ID: {}", stockId);
		List<TransactionLog> transactions = repository.findByStockIdIs(stockId);
		Stock stock = stockClient.viewStock(stockId);
		StockTransactionResponseDTO response = new StockTransactionResponseDTO(stock, transactions);
		logger.info("Transaction logs retrieved successfully for stock ID: {}", stockId);
		return response;

	}

	@Override
	public List<TransactionLog> getTransactionLogsByZone(int zoneId) {
		return repository.findByZoneIdIs(zoneId);

	}

	@Override
	public List<TransactionLog> getTransactionLogsByPriceBetween(Double initialPrice, Double finalPrice) {
		logger.info("Retrieving transaction logs between prices: {} and {}", initialPrice, finalPrice);
		List<TransactionLog> transactionLogs = repository.findByPriceBetween(initialPrice, finalPrice);
		logger.info("Transaction logs retrieved successfully between prices: {} and {}", initialPrice, finalPrice);
		return transactionLogs;

	}

	@Override
	public List<TransactionLog> getTransactionLogsByType(String type) {
		logger.info("Retrieving transaction logs by type: {}", type);
		List<TransactionLog> transactionLogs = repository.findByTypeIs(type);
		logger.info("Transaction logs retrieved successfully by type: {}", type);
		return transactionLogs;
	}

	@Override
	public String deleteTransactionLog(int transactionId) throws TransactionLogNotFound {
	    logger.info("Deleting transaction log with ID: {}", transactionId);
	    
	    Optional<TransactionLog> optionalTransaction = repository.findById(transactionId);
	    if (!optionalTransaction.isPresent()) {
	        logger.error("Transaction log not found with ID: {}", transactionId);
	        throw new TransactionLogNotFound("Transaction log not found");
	    }

	    TransactionLog transactionLog = optionalTransaction.get();
	    int stockId = transactionLog.getStockId();
	    Stock stock = stockClient.viewStock(stockId);

	    if (stock == null) {
	        logger.error("Stock item not found with ID: {}", stockId);
	        throw new StockNotFoundException("StockItem Not Found");
	    }

	    // Reverse the stock update
	    if (transactionLog.getType().equalsIgnoreCase("inbound")) {
	        stock.setStockQuantity(stock.getStockQuantity() - transactionLog.getQuantity());
	    } else if (transactionLog.getType().equalsIgnoreCase("outbound")) {
	        stock.setStockQuantity(stock.getStockQuantity() + transactionLog.getQuantity());
	    } else {
	        logger.error("Invalid transaction type: {}", transactionLog.getType());
	        throw new IllegalArgumentException("Invalid transaction type");
	    }

	    // Update the stock
	    stockClient.createStock(stock); // or use updateStockForInbound/Outbound if needed

	    // Delete the transaction
	    repository.deleteById(transactionId);
	    logger.info("Transaction log deleted and stock updated successfully: {}", transactionId);

	    return "Transaction deleted and stock updated successfully";
	}


	@Override
	public List<TransactionLog> getTransactionLogsByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate) {
		logger.info("Retrieving transaction logs between timestamps: {} and {}", startDate, endDate);
		List<TransactionLog> transactionLogs = repository.findByTimestampBetween(startDate, endDate);
		logger.info("Transaction logs retrieved successfully between timestamps: {} and {}", startDate, endDate);
		return transactionLogs;
	}

	@Override
	public UserTransactionResponseDTO getTransactionLogsByUser(int userId) {
		logger.info("Retrieving transaction logs for user ID: {}", userId);
		List<TransactionLog> transaction = repository.findByStockIdIs(userId);
		UserRole user = userClient.viewTransactionByUser(userId);
		UserTransactionResponseDTO responseDTO = new UserTransactionResponseDTO(user, transaction);
		logger.info("Transaction logs retrieved successfully for user ID: {}", userId);
		return responseDTO;
	}

}
