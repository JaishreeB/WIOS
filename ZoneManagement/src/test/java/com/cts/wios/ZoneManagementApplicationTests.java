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

import com.cts.wios.model.Zone;
import com.cts.wios.repository.ZoneRepository;
import com.cts.wios.service.ZoneServiceImpl;

@SpringBootTest
class ZoneManagementApplicationTests {
	@Mock
	ZoneRepository repository;

	@InjectMocks
	ZoneServiceImpl service;

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
	void getZoneTest() {
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
	void deleteZoneTest() {
		int zoneId = 10;
		Mockito.doNothing().when(repository).deleteById(zoneId);
		String response = service.deleteZone(zoneId);
		assertEquals("Zone deleted", response);
	}

}
