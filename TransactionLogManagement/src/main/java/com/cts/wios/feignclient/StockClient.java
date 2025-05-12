package com.cts.wios.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.wios.dto.Stock;

@FeignClient(name = "STOCKMANAGEMENT", path = "/stock")
public interface StockClient {
	@GetMapping("/fetchById/{id}")
	public Stock viewStock(@PathVariable("id") int stockId);
}