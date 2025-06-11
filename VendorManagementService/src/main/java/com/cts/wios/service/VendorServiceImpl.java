package com.cts.wios.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.wios.exceptions.VendorNotFound;
import com.cts.wios.model.Vendor;
import com.cts.wios.repository.VendorRepository;

/**
 * Service implementation for managing Vendor operations.
 */

@Service
public class VendorServiceImpl implements VendorService {

	/**
	 * Saves a new vendor to the repository. 
	 * @param vendor the vendor to be saved      
	 * @return confirmation message     
	 */

	@Autowired
	VendorRepository repository;
	public VendorServiceImpl(VendorRepository repository) {
		super();
		this.repository = repository;
	}

	Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

	@Override
	public String saveVendor(Vendor vendor) {
		logger.info("Saving vendor: {}", vendor);
		repository.save(vendor);
		logger.info("Vendor saved successfully.");
		return "Vendor Saved!!!";
	}

	/**
	 *Updates an existing vendor.
	 * @param vendor the vendor with updated details      
	 * @return the updated vendor     
	 */

	@Override
	public Vendor updateVendor(Vendor vendor) {
		logger.info("Updating vendor with ID: {}", vendor.getVendorId());
		Vendor updatedVendor = repository.save(vendor);
		logger.info("Vendor updated successfully: {}", updatedVendor);
		return updatedVendor;
	}

	/**
	 * Removes a vendor by ID.
	 * @param vendorId the ID of the vendor to be removed      
	 * @return confirmation message     
	 **/

	@Override
	public String removeVendor(int vendorId) {
		logger.warn("Deleting vendor with ID: {}", vendorId);
		repository.deleteById(vendorId);
		logger.info("Vendor deleted successfully.");
		return "Vendor Deleted!!!";
	}

	/**
	 * Retrieves a vendor by ID.
	 * @param userId the ID of the vendor      
	 * @return the vendor if found      
	 * @throws VendorNotFound if the vendor is not found     
	 **/

	@Override
	public Vendor getVendorById(int userId) throws VendorNotFound {
		logger.info("Fetching vendor with ID: {}", userId);
		Optional<Vendor> optional = repository.findById(userId);
		if (optional.isPresent()) {
			logger.info("Vendor found: {}", optional.get());
			return optional.get();
		} else {
			logger.error("Vendor with ID {} not found", userId);
			throw new VendorNotFound("Vendor is not Found........");

		}
	}

	/**
	 * Retrieves all vendors from the repository.
	 * @return list of all vendors     
	 */

	@Override
	public List<Vendor> getAllVendors() {
		logger.info("Fetching all vendors");
		List<Vendor> vendors = repository.findAll();
		logger.info("Total vendors found: {}", vendors.size());
		return vendors;
	}

}
