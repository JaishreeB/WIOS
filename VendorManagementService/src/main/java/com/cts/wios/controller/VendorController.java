package com.cts.wios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.wios.exceptions.VendorNotFound;
import com.cts.wios.model.Vendor;
import com.cts.wios.service.VendorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vendors")
@AllArgsConstructor
public class VendorController {
	@Autowired
	VendorService service;

	@PostMapping("/save")
	public String saveVendor(@RequestBody @Validated Vendor vendor) {
		return service.saveVendor(vendor);
	}

	@PutMapping("/update")
	public Vendor updateVendor(@RequestBody @Validated Vendor vendor) {
		return service.updateVendor(vendor);
	}

	@GetMapping("/fetchById/{id}")
	public Vendor getVendorById(@PathVariable("id") int vendorId) throws VendorNotFound {
		return service.getVendorById(vendorId);
	}

	@DeleteMapping("/deleteById/{id}") 
	public String removeVendor(@PathVariable("id") int vendorId) {
		return service.removeVendor(vendorId);
	}

	@GetMapping("/fetchAll")
	public List<Vendor> getAllVendors() {
		return service.getAllVendors();
	}

}
