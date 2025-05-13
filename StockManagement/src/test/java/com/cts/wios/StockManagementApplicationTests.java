package com.cts.wios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.wios.dto.StockVendorResponseDTO;
import com.cts.wios.dto.StockZoneResponseDTO;
import com.cts.wios.dto.Vendor;
import com.cts.wios.dto.Zone;
import com.cts.wios.exceptions.SpaceNotAvailable;
import com.cts.wios.exceptions.StockNotFoundException;
import com.cts.wios.exceptions.VendorNotFoundException;
import com.cts.wios.exceptions.ZoneNotFoundException;
import com.cts.wios.feignclient.VendorClient;
import com.cts.wios.feignclient.ZoneClient;
import com.cts.wios.model.Stock;
import com.cts.wios.repository.StockRepository;
import com.cts.wios.service.StockServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class StockManagementApplicationTests {

	@Mock
	private StockRepository repository;

	@Mock
	private ZoneClient zoneClient;

	@Mock
	private VendorClient vendorClient;

	@InjectMocks
	private StockServiceImpl stockService;

	private Stock stock;
	private Zone zone;

	@BeforeEach
	void setUp() {
		stock = new Stock();
		stock.setZoneId(1);
		stock.setStockQuantity(10);

		zone = new Zone();
		zone.setZoneId(1);
		zone.setAvailableSpace(10);
		zone.setZoneCapacity(200);
	}

	@Test
	void testCreateStockSuccess() throws SpaceNotAvailable, ZoneNotFoundException {
		when(zoneClient.viewZone(1)).thenReturn(zone);
		when(repository.save(stock)).thenReturn(stock);

		String result = stockService.createStock(stock);

		assertEquals("Stock saved successfully", result);
		verify(zoneClient).updateZone(any(Zone.class));
	}

	@Test
	void testCreateStockSpaceNotAvailable() {
	    // Set available space to a value that will cause the SpaceNotAvailable exception
	    zone.setAvailableSpace(5); // Assuming stock quantity is 10, this will cause the exception
	    when(zoneClient.viewZone(1)).thenReturn(zone);

	    assertThrows(SpaceNotAvailable.class, () -> stockService.createStock(stock));
	}


	@Test
	void testCreateStockZoneNotFound() {
		when(zoneClient.viewZone(1)).thenThrow(new RuntimeException());

		assertThrows(ZoneNotFoundException.class, () -> stockService.createStock(stock));
	}

	@Test
	void testUpdateStockForInboundSuccess() throws SpaceNotAvailable, ZoneNotFoundException {
		when(zoneClient.viewZone(1)).thenReturn(zone);

		Stock result = stockService.updateStockForInbound(stock);

		assertEquals(stock, result);
		verify(zoneClient).updateZone(any(Zone.class));
	}

	@Test
	void testUpdateStockForOutboundSuccess() throws SpaceNotAvailable, ZoneNotFoundException {
		when(zoneClient.viewZone(1)).thenReturn(zone);
		when(repository.save(stock)).thenReturn(stock);

		Stock result = stockService.updateStockForOutbound(stock);

		assertEquals(stock, result);
		verify(zoneClient).updateZone(any(Zone.class));
	}

	@Test
	void testViewStockSuccess() throws StockNotFoundException {
		when(repository.findById(1)).thenReturn(Optional.of(stock));

		Stock result = stockService.viewStock(1);

		assertEquals(stock, result);
	}

	@Test
	void testViewStockNotFound() {
		when(repository.findById(1)).thenReturn(Optional.empty());

		assertThrows(StockNotFoundException.class, () -> stockService.viewStock(1));
	}

	@Test
	void testDeleteStockSuccess() throws StockNotFoundException {
		when(repository.findById(1)).thenReturn(Optional.of(stock));
		when(zoneClient.viewZone(1)).thenReturn(zone);

		String result = stockService.deleteStock(1);

		assertEquals("StockItem Deleted and updated the zone capacity!!!", result);
		verify(repository).deleteById(1);
	}

	@Test
	void testDeleteStockNotFound() {
		when(repository.findById(1)).thenReturn(Optional.empty());

		assertThrows(StockNotFoundException.class, () -> stockService.deleteStock(1));
	}

	@Test
	void testGetAllStocks() {
		List<Stock> stocks = Arrays.asList(stock);
		when(repository.findAll()).thenReturn(stocks);

		List<Stock> result = stockService.getAllStocks();

		assertEquals(stocks, result);
	}

	@Test
	void testGetStocksByCategory() {
		List<Stock> stocks = Arrays.asList(stock);
		when(repository.findByStockCategoryIs("category")).thenReturn(stocks);

		List<Stock> result = stockService.getStocksByCategory("category");

		assertEquals(stocks, result);
	}

	@Test
	void testGetStocksByZoneSuccess() throws ZoneNotFoundException {
		List<Stock> stocks = Arrays.asList(stock);
		when(zoneClient.viewZone(1)).thenReturn(zone);
		when(repository.findByZoneIdIs(1)).thenReturn(stocks);

		StockZoneResponseDTO result = stockService.getStocksByZone(1);

		assertEquals(stocks, result.getStock());
		assertEquals(zone, result.getZone());
	}

	@Test
	void testGetStocksByZoneNotFound() {
		when(zoneClient.viewZone(1)).thenThrow(new ZoneNotFoundException("Zone ID not found"));

		assertThrows(ZoneNotFoundException.class, () -> stockService.getStocksByZone(1));
	}

	@Test
	void testGetStocksByVendorSuccess() throws VendorNotFoundException {
		List<Stock> stocks = Arrays.asList(stock);
		Vendor vendor = new Vendor();
		when(vendorClient.getVendorById(1)).thenReturn(vendor);
		when(repository.findByVendorIdIs(1)).thenReturn(stocks);

		StockVendorResponseDTO result = stockService.getStocksByVendor(1);

		assertEquals(stocks, result.getStock());
		assertEquals(vendor, result.getVendor());
	}

	@Test
	void testGetStocksByVendorNotFound() {
		when(vendorClient.getVendorById(1)).thenThrow(new VendorNotFoundException("Vendor ID not found"));

		assertThrows(VendorNotFoundException.class, () -> stockService.getStocksByVendor(1));
	}
}
