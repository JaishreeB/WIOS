package com.cts.wios.dto;

import java.util.List;

import com.cts.wios.model.Stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockVendorResponseDTO {
	private List<Stock> stock;
	private Vendor vendor;
}
