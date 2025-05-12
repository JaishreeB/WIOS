package com.cts.wios.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.cts.wios.dto.Zone;

@FeignClient(name="ZONEMANAGEMENT",path="/zone")
public interface ZoneClient {
	@GetMapping("/fetchById/{id}")
	public Zone viewZone(@PathVariable("id") int warehouseZoneId);
	@PutMapping("/update")
	public void updateZone(Zone zone);

}
