package com.cts.wios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.wios.dto.StockVendorResponseDTO;
import com.cts.wios.dto.StockZoneResponseDTO;
import com.cts.wios.exceptions.SpaceNotAvailable;
import com.cts.wios.exceptions.StockNotFound;
import com.cts.wios.model.Stock;
import com.cts.wios.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {
	@Autowired
	StockService service;

	@PostMapping("/save")
	public String createStock(@RequestBody Stock stock) throws SpaceNotAvailable {
		return service.createStock(stock);
	}

	@PutMapping("/updateInbound")
	public Stock updateStockForInbound(@RequestBody Stock stock) {
		return service.updateStockForInbound(stock);
	}
	
	@PutMapping("/updateOutbound")
	public Stock updateStockForOutbound(@RequestBody Stock stock) {
		return service.updateStockForOutbound(stock);
	}

	@GetMapping("/fetchById/{id}")
	public Stock viewStock(@PathVariable("id") int stockId) throws StockNotFound {
		return service.viewStock(stockId);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteStock(@PathVariable("id") int stockId) throws StockNotFound {
		return service.deleteStock(stockId);

	}

	@GetMapping("/fetchAll")
	public List<Stock> getAllStocks() {
		return service.getAllStocks();
	}

	@GetMapping("/fetchCategory/{category}")
	public List<Stock> getStocksByCategory(@PathVariable("category") String category) {
		return service.getStocksByCategory(category);

	}

	@GetMapping("/fetchZone/{zone}")
	public StockZoneResponseDTO getStocksByZone(@PathVariable("zone") int zoneId) {
		return service.getStocksByZone(zoneId);
	}
	
	@GetMapping("/fetchVendor/{vendor}")
	public StockVendorResponseDTO getStocksByVendor(@PathVariable("vendor") int vendorId) {
		return service.getStocksByVendor(vendorId);
	}

}
