package com.cts.wios.dto.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.wios.dto.Zone;

@FeignClient(name="ZONEMANAGEMENT",path="/zone")
public interface ZoneClient {
	@GetMapping("/fetchById/{id}")
	public Zone viewZone(@PathVariable("id") int warehouseZoneId);
	@GetMapping("/fetchAll")
	public List<Zone> viewAll();	
}
