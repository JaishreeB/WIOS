package com.cts.wios.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.wios.dto.Stock;
import com.cts.wios.dto.StockZoneResponseDTO;
import com.cts.wios.exceptions.ZoneNotFound;
import com.cts.wios.feignclient.StockClient;
import com.cts.wios.model.Zone;
import com.cts.wios.repository.ZoneRepository;

@Service
//@AllArgsConstructor

public class ZoneServiceImpl implements ZoneService {
	@Autowired
	ZoneRepository repository;
	@Autowired
	StockClient stockClient;
	
	Logger logger = LoggerFactory.getLogger(ZoneServiceImpl.class);

	/*
	 * Create a new zone.
	 * 
	 * @param zone the zone to be created
	 * 
	 * @return a message indicating the zone was saved successfully
	 */

	@Override
	public String createZone(Zone zone) {
		logger.info("Creating zone: {}", zone);
		repository.save(zone);
		logger.info("Zone saved successfully: {}", zone);
		return "Zone saved successfully";
	}

	/*
	 *  Update an existing zone.        
	 * 
	 * @param zone the zone to be updated
	 * 
	 * @return the updated zone     
	 */

	@Override
	public Zone updateZone(Zone zone) {
		logger.info("Updating zone: {}", zone);
		return repository.save(zone);
	}

	/*
	 * View a zone by its ID.
	 * 
	 * @param warehouseZoneId the ID of the zone to be viewed     
	 * 
	 * @return the zone if found     
	 * 
	 * @throws ZoneNotFound if the zone is not found     
	 */

	@Override
	public Zone viewZone(int warehouseZoneId) throws ZoneNotFound {
		logger.info("Viewing zone with ID: {}", warehouseZoneId);
		Optional<Zone> optional = repository.findById(warehouseZoneId);
		if (optional.isPresent()) {
			logger.info("Zone found: {}", optional.get());
			return optional.get();
		}
		else {
			logger.error("Zone not found with ID: {}", warehouseZoneId);
			throw new ZoneNotFound("zone not found");
		}
	}

	/*
	 * Delete a zone by its ID.
	 * 
	 * @param warehouseZoneId the ID of the zone to be deleted     
	 * 
	 * @return a message indicating the zone was deleted or not found     
	 */

	@Override
	public String deleteZone(int warehouseZoneId) {
		logger.info("Deleting zone with ID: {}", warehouseZoneId);
		if (repository.existsById(warehouseZoneId)) {
			StockZoneResponseDTO responseDTO = stockClient.getStocksByZone(warehouseZoneId);
			List<Stock> stocks = responseDTO.getStock();
			for (Stock stock : stocks) {
				logger.info("Deleting stock with ID: {}", stock.getStockId());
				stockClient.deleteStock(stock.getStockId());
			}

			repository.deleteById(warehouseZoneId);
			logger.info("Zone deleted successfully with ID: {}", warehouseZoneId);
			return "Zone and all the stocks present in the zone also deleted";
		} else {
			logger.error("Zone not found with ID: {}", warehouseZoneId);
			return "Zone not found";
		}
	}

	/*
	 * Get all zones.
	 * 
	 * @return a list of all zones     
	 */

	@Override
	public List<Zone> getAllZone() {
		logger.info("Fetching all zones");
		return repository.findAll();
	}

}
