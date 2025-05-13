package com.cts.wios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
	private int stockId;
	private String stockName;
	private String stockCategory;
	private int stockQuantity;
	private int zoneId;
	private int vendorId;
}
