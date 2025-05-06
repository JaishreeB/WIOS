package com.cts.wios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.wios.dto.StockVendorResponseDTO;
import com.cts.wios.dto.StockZoneResponseDTO;
import com.cts.wios.dto.Vendor;
import com.cts.wios.dto.Zone;
import com.cts.wios.feignclient.VendorClient;
import com.cts.wios.feignclient.ZoneClient;
import com.cts.wios.model.Stock;
import com.cts.wios.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {
	@Autowired
	StockRepository repository;
	
	@Autowired
	ZoneClient zoneClient;
	
	@Autowired
	VendorClient vendorClient;
	@Override
	public String createStock(Stock stock) {
		repository.save(stock);
		return "Stock saved successfully";
	}

	@Override
	public Stock updateStock(Stock stock) {
		return repository.save(stock);
	}

	@Override
	public Stock viewStock(int stockId) {
		Optional<Stock> optional = repository.findById(stockId);
//		if (optional.isPresent())
//			return optional.get();
//		else
//			throw new ZoneNotFound("Invalid Id");
		return optional.get();
	}

	@Override
	public String deleteStock(int stock) {
		repository.deleteById(stock);
		return "Stock deleted";
	}

	@Override
	public List<Stock> getAllStocks() {
		return repository.findAll();
	}

	@Override
	public List<Stock> getStocksByCategory(String category) {
		return repository.findByStockCategoryIs(category);
	}

	@Override
	public StockZoneResponseDTO getStocksByZone(int zoneId) {
		Zone zone=zoneClient.viewZone(zoneId);
		List<Stock> stocks=repository.findByZoneIdIs(zoneId);
		StockZoneResponseDTO responseDTO=new StockZoneResponseDTO(stocks,zone);
		return responseDTO;
		
	}

	@Override
	public StockVendorResponseDTO getStocksByVendor(int vendorId) { 
		Vendor vendor=vendorClient.getVendorById(vendorId);
		List<Stock> stocks=repository.findByVendorIdIs(vendorId);
		StockVendorResponseDTO responseDTO=new StockVendorResponseDTO(stocks,vendor);
		return responseDTO;
	}

}
