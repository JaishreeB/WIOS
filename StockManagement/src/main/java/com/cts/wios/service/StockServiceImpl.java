package com.cts.wios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.wios.dto.StockVendorResponseDTO;
import com.cts.wios.dto.StockZoneResponseDTO;
import com.cts.wios.dto.Vendor;
import com.cts.wios.dto.Zone;
import com.cts.wios.exceptions.SpaceNotAvailable;
import com.cts.wios.exceptions.StockNotFound;
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
	
	int UpdateCapacity;
	
	@Override
	public String createStock(Stock stock) throws SpaceNotAvailable {
		repository.save(stock);
		int zoneId = stock.getZoneId();
		Zone zone = zoneClient.viewZone(zoneId);
		UpdateCapacity = zone.getAvailableSpace() - stock.getStockQuantity();
		if(zone.getZoneCapacity()>UpdateCapacity) {	
		zone.setAvailableSpace(UpdateCapacity);
		zoneClient.updateZone(zone);
		}
		else {
			throw new SpaceNotAvailable("Space not available to store the stock!!!!");
		}
		return "Stock saved successfully";
	}

	@Override
	public Stock updateStockForInbound(Stock stock) {
		int zoneId = stock.getZoneId();
		Zone zone = zoneClient.viewZone(zoneId);
		UpdateCapacity = zone.getAvailableSpace() - stock.getStockQuantity();
		zone.setAvailableSpace(UpdateCapacity);
		zoneClient.updateZone(zone);
		return repository.save(stock);
	}
	
	@Override
	public Stock updateStockForOutbound(Stock stock) {
		int zoneId = stock.getZoneId();
		Zone zone = zoneClient.viewZone(zoneId);
		UpdateCapacity = zone.getAvailableSpace() + stock.getStockQuantity();
		zone.setAvailableSpace(UpdateCapacity);
		zoneClient.updateZone(zone);
		return repository.save(stock);
	}

	@Override
	public Stock viewStock(int stockId) throws StockNotFound{
		Optional<Stock> optional = repository.findById(stockId);
		if (optional.isPresent())
			return optional.get();
		else
			throw new StockNotFound("Invalid Id");
	}

	@Override
	public String deleteStock(int stock) throws StockNotFound {
		Stock stockItem=repository.findById(stock).get();
		if(stockItem==null) {
			throw new StockNotFound("Stock Item not found");
		}
		int zoneId = stockItem.getZoneId();
		Zone zone = zoneClient.viewZone(zoneId);
		UpdateCapacity = zone.getAvailableSpace() - stockItem.getStockQuantity();
		zone.setAvailableSpace(UpdateCapacity);
		zoneClient.updateZone(zone);
		repository.deleteById(stock);
		return "StockItem Deleted and updated the zone capacity!!!";
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
