package com.cts.wios.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int transactionId;
	private int stockId;
	private String stockName;
	private int userId;
	private String userName;
	private int zoneId;
	private String zoneName;
	private int quantity;
	private String type;
	@CreationTimestamp
	private LocalDateTime timestamp;
	private double price;

	public TransactionLog(int userId, int transactionId, int stockId, int zoneId, int quantity, String type,
			double price) {
		super();
		this.transactionId = transactionId;
		this.stockId = stockId;
		this.userId = userId;
		this.zoneId = zoneId;
		this.quantity = quantity;
		this.type = type;
		this.price = price;
	}

}
