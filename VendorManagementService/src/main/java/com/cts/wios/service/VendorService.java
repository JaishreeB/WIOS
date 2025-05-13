package com.cts.wios.service;

import java.util.List;

import com.cts.wios.exceptions.VendorNotFound;
import com.cts.wios.model.Vendor;

public interface VendorService {
	public abstract String saveVendor(Vendor vendor);

	public abstract Vendor updateVendor(Vendor vendor);

	public abstract String removeVendor(int vendorId);

	public abstract Vendor getVendorById(int vendorId) throws VendorNotFound;

	public abstract List<Vendor> getAllVendors();

}
