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

import com.cts.wios.exceptions.ZoneNotFound;
import com.cts.wios.model.Zone;
import com.cts.wios.service.ZoneService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/zone")
@AllArgsConstructor
public class ZoneController {
	@Autowired
	ZoneService service;

	// http://localhost:9090/zone/save
	@PostMapping("/save")
	public String createZone(@RequestBody Zone zone) {
		return service.createZone(zone);
	}

	// http://localhost:9090/zone/update
	@PutMapping("/update")
	public Zone updateZone(@RequestBody Zone zone) {
		return service.updateZone(zone);
	}

	// http://localhost:9090/zone/fetchById/1
	@GetMapping("/fetchById/{id}")
	public Zone viewZone(@PathVariable("id") int warehouseZoneId) throws ZoneNotFound {
		return service.viewZone(warehouseZoneId);
	}

	// http://localhost:9090/zone/delete/1
	@DeleteMapping("/delete/{id}")
	public String deleteZone(@PathVariable("id") int warehouseZoneId) {
		return service.deleteZone(warehouseZoneId);
	}

	// http://localhost:9090/zone/fetchAll
	@GetMapping("/fetchAll")
	public List<Zone> getAllZone() {
		return service.getAllZone();
	}
	@GetMapping("/fetchZoneNameById/{id}")
	public String getZoneNameById(@PathVariable("id") int warehouseZoneId) throws ZoneNotFound {
		return service.getZoneNameById(warehouseZoneId);
	}

}
