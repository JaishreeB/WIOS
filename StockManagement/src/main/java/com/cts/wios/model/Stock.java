package com.cts.wios.model;

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
@Table(name = "stock_info")
public class Stock {
	@Id
	private int stockId;
	private String stockName;
	private String stockCategory;
	private int stockQuantity;
	private int zoneId;
}
