package com.cts.wios.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.wios.dto.StockZoneResponseDTO;

@FeignClient(name = "STOCKMANAGEMENT", path = "/stock")
public interface StockClient {
	@GetMapping("/fetchZone/{zone}")
	public StockZoneResponseDTO getStocksByZone(@PathVariable("zone") int zoneId);
	@DeleteMapping("/delete/{id}")
	public String deleteStock(@PathVariable("id") int stockId);
}
