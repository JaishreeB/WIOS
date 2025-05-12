package com.cts.wios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.wios.dto.Stock;
import com.cts.wios.dto.StockZoneResponseDTO;
import com.cts.wios.exceptions.ZoneNotFound;
import com.cts.wios.feignclient.StockClient;
import com.cts.wios.model.Zone;
import com.cts.wios.repository.ZoneRepository;
import com.cts.wios.service.ZoneServiceImpl;

@SpringBootTest
class ZoneManagementApplicationTests {
	@Mock
	ZoneRepository repository;

	@InjectMocks
	ZoneServiceImpl service;
	@Mock
	StockClient stockClient;
	@Mock
	StockZoneResponseDTO responseDTO;

	@Test
	void createZoneTest() {
		Zone zone = new Zone(10, "hazmat zone", 100, 100);
		Mockito.when(repository.save(zone)).thenReturn(zone);
		String response = service.createZone(zone);
		assertEquals("Zone saved successfully", response);
	}

	@Test
	void updateZoneTest() {
		Zone zone = new Zone(10, "hazmat zone", 150, 100);
		Mockito.when(repository.save(zone)).thenReturn(zone);
		Zone response = service.updateZone(zone);
		assertEquals(zone, response);
	}

	@Test
	void getZoneTest() throws ZoneNotFound {
		int zoneId = 10;
		Zone zone = new Zone(10, "hazmat zone", 150, 100);
		Mockito.when(repository.findById(zoneId)).thenReturn(Optional.of(zone));
		Zone response = service.viewZone(zoneId);
		assertEquals(zone, response);
	}

	@Test
	void getAllZoneTest() {
		List<Zone> zones = Arrays.asList(new Zone(10, "hazmat zone", 150, 100), new Zone(11, "cold zone", 150, 100));
		Mockito.when(repository.findAll()).thenReturn(zones);
		List<Zone> response = service.getAllZone();
		assertEquals(zones, response);
	}


	@Test
	void deleteZone_SuccessTest() {
		Zone zone = new Zone(1, "soaps", 5, 3);
		Mockito.when(repository.existsById(zone.getZoneId())).thenReturn(true);
		Mockito.when(stockClient.getStocksByZone(zone.getZoneId())).thenReturn(responseDTO);
		for (Stock stock : responseDTO.getStock()) {
			Mockito.doNothing().when(stockClient).deleteStock(stock.getStockId());
       }
		Mockito.doNothing().when(repository).deleteById(zone.getZoneId());
		String result = service.deleteZone(zone.getZoneId());
		assertEquals("Zone and all the stocks present in the zone also deleted", result);
	}

	@Test
	void deleteZone_NotFoundTest() {
		Zone zone = new Zone(1, "soaps", 5, 3);
		Mockito.when(repository.existsById(zone.getZoneId())).thenReturn(false);
		String result = service.deleteZone(zone.getZoneId());
		assertEquals("Zone not found", result);
	}
	@Test
	void getZoneNotFoundTest() {
		int zoneId = 1;

		Mockito.when(repository.findById(zoneId)).thenReturn(Optional.empty());

		assertThrows(ZoneNotFound.class, () -> {
			service.viewZone(zoneId);
		});
	}

}
