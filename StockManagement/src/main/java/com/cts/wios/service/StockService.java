package com.cts.wios.service;

import java.util.List;

import com.cts.wios.dto.StockVendorResponseDTO;
import com.cts.wios.dto.StockZoneResponseDTO;
import com.cts.wios.model.Stock;

public interface StockService {
	public String createStock(Stock stock);

	public Stock updateStock(Stock stock);

	public Stock viewStock(int stockId);

	public String deleteStock(int stockeId);

	public List<Stock> getAllStocks();

	public List<Stock> getStocksByCategory(String category);

	public StockZoneResponseDTO getStocksByZone(int zoneId);

	public StockVendorResponseDTO getStocksByVendor(int vendorId);
}
