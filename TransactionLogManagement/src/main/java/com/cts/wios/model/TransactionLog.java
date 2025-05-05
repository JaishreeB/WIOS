package com.cts.wios.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction_log_info")
public class TransactionLog {
	@Id
	private int transactionId;
	private int stockId;
	private int vendorId;
	private int userId;
//	private int zoneId;
	private int quantity;
	private String type;
	@CreationTimestamp
	private LocalDateTime timestamp;
	private double price;

	public TransactionLog(int userId, int transactionId, int stockId, int vendorId, int quantity, String type,
			double price) {
		super();
		this.transactionId = transactionId;
		this.stockId = stockId;
		this.userId = userId;
		this.vendorId = vendorId;
		this.quantity = quantity;
		this.type = type;
		this.price = price;
	}

}
