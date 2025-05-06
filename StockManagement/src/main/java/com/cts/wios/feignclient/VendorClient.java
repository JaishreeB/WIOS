package com.cts.wios.feignclient;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.wios.dto.Vendor;

@FeignClient(name="VENDORMANAGEMENTSERVICE",path="/vendors")
public interface VendorClient {
	@GetMapping("/fetchById/{id}")
	public Vendor getVendorById(@PathVariable("id") int vendorId);
}