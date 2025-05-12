package com.cts.wios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.wios.dto.StockZoneResponseDTO;
import com.cts.wios.exceptions.SpaceNotAvailable;
import com.cts.wios.exceptions.StockNotFound;
import com.cts.wios.model.Stock;
import com.cts.wios.repository.StockRepository;
import com.cts.wios.service.StockServiceImpl;

@SpringBootTest
class StockManagementApplicationTests {
	@Mock
	StockRepository repository;

	@InjectMocks
	StockServiceImpl service;

	@Test
	void createStockTest() throws SpaceNotAvailable {
		Stock stock = new Stock(10,"chocolate","sweet",50,1,1);
		Mockito.when(repository.save(stock)).thenReturn(stock);
		String response = service.createStock(stock);
		assertEquals("Stock saved successfully", response);
	}

	@Test
	void updateStockTest() {
		Stock stock = new Stock(10,"chocolate","sweet",50,1,1);
		Mockito.when(repository.save(stock)).thenReturn(stock);
		Stock response = service.updateStockForInbound(stock);
		assertEquals(stock, response);
	}

	@Test
	void getStockTest() throws StockNotFound {
		int stockId = 10;
		Stock stock = new Stock(10,"chocolate","sweet",50,1,1);
		Mockito.when(repository.findById(stockId)).thenReturn(Optional.of(stock));
		Stock response = service.viewStock(stockId);
		assertEquals(stock, response);
	}

	@Test
	void getAllStockTest() {
		List<Stock> stocks = Arrays.asList(new Stock(10,"chocolate","sweet",50,1,1), new Stock(11,"chocolate","sweet",50,1,1));
		Mockito.when(repository.findAll()).thenReturn(stocks);
		List<Stock> response = service.getAllStocks();
		assertEquals(stocks, response);
	}

	@Test
	void deleteStockTest() throws StockNotFound {
		int stockId = 10;
		Mockito.doNothing().when(repository).deleteById(stockId);
		String response = service.deleteStock(stockId);
		assertEquals("Stock deleted", response);
	}
	
	@Test
	void getStocksByCategoryTest() {
		List<Stock> stocks = Arrays.asList(new Stock(10,"chocolate","sweet",50,1,1), new Stock(11,"jamun","sweet",50,1,1));
		Mockito.when(repository.findByStockCategoryIs("sweet")).thenReturn(stocks);
		List<Stock> response = service.getStocksByCategory("sweet");
		assertEquals(stocks, response);
	}
	
	@Test
	void getStocksByZoneTest() {
		int zoneId=1;
		List<Stock> stocks = Arrays.asList(new Stock(10,"chocolate","sweet",50,1,1), new Stock(11,"jamun","sweet",50,1,1));
		Mockito.when(repository.findByZoneIdIs(zoneId)).thenReturn(stocks);
		StockZoneResponseDTO response = service.getStocksByZone(zoneId);
		assertEquals(stocks, response);
	}
	
//	@Test
//	void getStocksByVendorTest() {
//		List<Stock> stocks = Arrays.asList(new Stock(10,"chocolate","sweet",50,1,1), new Stock(11,"jamun","sweet",50,1,1));
//		Mockito.when(repository.findByVendorIdIs(1)).thenReturn(stocks);
//		List<Stock> response = service.getStocksByVendor(1);
//		assertEquals(stocks, response);
//	}
	
	
	
	

}
