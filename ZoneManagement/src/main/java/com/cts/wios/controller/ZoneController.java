package com.cts.wios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.wios.model.Zone;
import com.cts.wios.service.ZoneService;

@RestController
@RequestMapping("/zone")
public class ZoneController {
	@Autowired
	ZoneService service;

	@PostMapping("/save")
	public String createZone(Zone zone) {
		return service.createZone(zone);
	}

	@PutMapping("/update")
	public Zone updateZone(Zone zone) {
		return service.updateZone(zone);
	}

	@GetMapping("/fetchById/{id}")
	public Zone viewZone(@PathVariable("id") int warehouseZoneId) {
		return service.viewZone(warehouseZoneId);
	}

	@DeleteMapping("/delete")
	public String deleteZone(@PathVariable("id") int warehouseZoneId) {
		return service.deleteZone(warehouseZoneId);

	}

	@GetMapping("/fetchAll")
	public List<Zone> getAllZone() {
		return service.getAllZone();
	}

}
