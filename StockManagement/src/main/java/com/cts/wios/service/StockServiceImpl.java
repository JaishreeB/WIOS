package com.cts.wios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.wios.model.Stock;
import com.cts.wios.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {
	@Autowired
	StockRepository repository;

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
	public List<Stock> getStocksByZone(int zoneId) {
		return repository.findByZoneIdIs(zoneId);
	}

}
