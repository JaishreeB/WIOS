package com.cts.wios.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.wios.dto.Stock;

@FeignClient(name = "STOCKMANAGEMENT", path = "/stock")
public interface StockClient {
	
	@GetMapping("/fetchById/{id}")
	public Stock viewStock(@PathVariable("id") int stockId);

	@PutMapping("/updateInbound")
	public Stock updateStockForInbound(@RequestBody @Validated Stock stockItem);

	@PutMapping("/updateOutbound")
	public Stock updateStockForOutbound(@RequestBody @Validated Stock stockItem);

	@PostMapping("/save")
	public String createStock(@RequestBody @Validated Stock stockItem);
}