package com.cts.wios.service;

import java.util.List;

import com.cts.wios.dto.StockVendorResponseDTO;
import com.cts.wios.dto.StockZoneResponseDTO;
import com.cts.wios.exceptions.SpaceNotAvailable;
import com.cts.wios.exceptions.StockNotFoundException;
import com.cts.wios.model.Stock;

public interface StockService {
	public String createStock(Stock stock) throws SpaceNotAvailable;

	public Stock updateStockForInbound(Stock stock) throws SpaceNotAvailable;

	public Stock viewStock(int stockId) throws StockNotFoundException;

	public String deleteStock(int stockeId) throws StockNotFoundException;

	public List<Stock> getAllStocks();

	public List<Stock> getStocksByCategory(String category);

	public StockZoneResponseDTO getStocksByZone(int zoneId);

	public StockVendorResponseDTO getStocksByVendor(int vendorId);

	public Stock updateStockForOutbound(Stock stock) throws SpaceNotAvailable;
}
