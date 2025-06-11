package com.cts.wios.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.wios.dto.AdminNotificationRequest;
import com.cts.wios.dto.StockVendorResponseDTO;
import com.cts.wios.dto.StockZoneResponseDTO;
import com.cts.wios.dto.Vendor;
import com.cts.wios.dto.VendorNotificationRequest;
import com.cts.wios.dto.Zone;
import com.cts.wios.exceptions.SpaceNotAvailable;
import com.cts.wios.exceptions.StockNotFoundException;
import com.cts.wios.exceptions.VendorNotFoundException;
import com.cts.wios.exceptions.ZoneNotFoundException;
import com.cts.wios.feignclient.NotificationClient;
import com.cts.wios.feignclient.UserInfoClient;
import com.cts.wios.feignclient.VendorClient;
import com.cts.wios.feignclient.ZoneClient;
import com.cts.wios.model.Stock;
import com.cts.wios.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {

	public StockServiceImpl(StockRepository repository, ZoneClient zoneClient, VendorClient vendorClient) {
		super();
		this.repository = repository;
		this.zoneClient = zoneClient;
		this.vendorClient = vendorClient;
	}

	@Autowired
	NotificationClient notificationClient;

	@Autowired
	StockRepository repository;
	@Autowired
	ZoneClient zoneClient;
	@Autowired
	VendorClient vendorClient;
	@Autowired
	UserInfoClient userClient;

	private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

	int UpdateCapacity;

	/**
	 * Creates a new stock and updates the zone capacity.
	 * 
	 * @param stock The stock to be created.
	 * @return A success message.
	 * @throws SpaceNotAvailable     If there is not enough space in the zone.
	 * @throws ZoneNotFoundException If the zone ID is not found.
	 */
	@Override
	public String createStock(Stock stock) throws SpaceNotAvailable, ZoneNotFoundException {

		logger.info("Creating stock: {}", stock);
		repository.save(stock);
		int zoneId = stock.getZoneId();
		Zone zone;
		try {
			zone = zoneClient.viewZone(zoneId);
			UpdateCapacity = zone.getAvailableSpace() - stock.getStockQuantity();
			if (UpdateCapacity >= 0) { // Ensure UpdateCapacity is non-negative
				zone.setAvailableSpace(UpdateCapacity);
				zoneClient.updateZone(zone);
			} else {
				logger.error("Space not available to store the stock: {}", stock);
				throw new SpaceNotAvailable("Space not available to store the stock!!!!");
			}
			logger.info("Stock saved successfully: {}", stock);
			return "Stock saved successfully";
		} catch (RuntimeException e) {
			logger.error("Zone ID not found for: {}", zoneId);
			throw new ZoneNotFoundException("Zone ID not found");
		}
	}

	/**
	 * Updates stock for In bound transactions and updates the zone capacity.
	 * 
	 * @param stock The stock to be updated.
	 * @return The updated stock.
	 * @throws SpaceNotAvailable     If there is not enough space in the zone.
	 * @throws ZoneNotFoundException If the zone ID is not found.
	 */
	@Override
	public Stock updateStockForInbound(Stock stock) throws SpaceNotAvailable, ZoneNotFoundException {
		logger.info("Updating stock for inbound: {}", stock);
		int zoneId = stock.getZoneId();
		Stock oldStock = viewStock(stock.getStockId());
		Zone zone;

		try {
			zone = zoneClient.viewZone(zoneId);
			logger.error("IN INBOUND ZONE BEFORE UPDATE..........: {}", zone);
			UpdateCapacity = (zone.getAvailableSpace() + oldStock.getStockQuantity()) - stock.getStockQuantity();
			if (UpdateCapacity >= 0) { // Ensure UpdateCapacity is non-negative

				zone.setAvailableSpace(UpdateCapacity);
				zoneClient.updateZone(zone);
				logger.error("IN INBOUND ZONE UPDATE..........: {}", zone);
			} else {
				logger.error("Space not available to store the stock: {}", stock);
				throw new SpaceNotAvailable("Space not available to store the stock!!!!!");
			}
			logger.info("Stock saved successfully: {}", stock);

			return repository.save(stock);
		} catch (RuntimeException e) {
			logger.error("Zone ID not found: {}", zoneId);
			throw new ZoneNotFoundException("Zone ID not found");
		}
	}

	/**
	 * Updates stock for out bound transactions and updates the zone capacity.
	 * 
	 * @param stock The stock to be updated.
	 * @return The updated stock.
	 * @throws SpaceNotAvailable     If there is not enough space in the zone.
	 * @throws ZoneNotFoundException If the zone ID is not found.
	 */
	@Override
	public Stock updateStockForOutbound(Stock stock) throws ZoneNotFoundException {
		logger.info("Updating stock for outbound: {}", stock);
		int zoneId = stock.getZoneId();
		Stock oldStock = viewStock(stock.getStockId());
		logger.error("IN OUTBOUND old stock for UPDATE..........: {}", oldStock);
		Zone zone;
		try {
			zone = zoneClient.viewZone(zoneId);

			UpdateCapacity = zone.getAvailableSpace() + (oldStock.getStockQuantity() - stock.getStockQuantity());
			logger.error("IN OUTBOUND before ZONE UPDATE..........: {}", zone);
			zone.setAvailableSpace(UpdateCapacity);
			zoneClient.updateZone(zone);
			logger.error("IN OUTBOUND ZONE UPDATE..........: {}", zone);

			logger.info("Stock updated for outbound: {}", stock);
			Stock updatedStock=repository.save(stock);
			if (stock.getStockQuantity() <= 10) {
				triggerStockNotification(stock);
			}
			return updatedStock;
		} catch (RuntimeException e) {
			logger.error("Zone ID not found: {}", zoneId);
			throw new ZoneNotFoundException("Zone ID not found");
		}
	}

	/**
	 * Views a stock by its ID.
	 * 
	 * @param stockId The ID of the stock to be viewed.
	 * @return The stock.
	 * @throws StockNotFoundException If the stock is not found.
	 */
	@Override
	public Stock viewStock(int stockId) throws StockNotFoundException {
		logger.info("Viewing stock with ID: {}", stockId);
		Optional<Stock> optional = repository.findById(stockId);
		if (optional.isPresent()) {
			logger.info("Stock found: {}", optional.get());
			return optional.get();
		} else {
			logger.error("Stock not found with ID: {}", stockId);
			throw new StockNotFoundException("Invalid stock Id");
		}
	}

	/**
	 * Deletes a stock by its ID and updates the zone capacity.
	 * 
	 * @param stock The ID of the stock to be deleted.
	 * @return A success message.
	 * @throws StockNotFoundException If the stock is not found.
	 */
	@Override
	public String deleteStock(int stock) throws StockNotFoundException {
		logger.info("Deleting stock with ID: {}", stock);
		Stock stockItem = repository.findById(stock).orElse(null);
		if (stockItem == null) {
			logger.error("Stock item not found with ID: {}", stock);
			throw new StockNotFoundException("Stock Item not found");
		}
		int zoneId = stockItem.getZoneId();
		Zone zone = zoneClient.viewZone(zoneId);
		UpdateCapacity = zone.getAvailableSpace() + stockItem.getStockQuantity();
		zone.setAvailableSpace(UpdateCapacity);
		zoneClient.updateZone(zone);
		repository.deleteById(stock);
		logger.info("Stock item deleted and zone capacity updated: {}", stockItem);
		return "StockItem Deleted and updated the zone capacity!!!";
	}

	/**
	 * Gets all stocks.
	 * 
	 * @return A list of all stocks.
	 */
	@Override
	public List<Stock> getAllStocks() {
		logger.info("Getting all stocks");
		return repository.findAll();
	}

	/**
	 * Gets stocks by category.
	 * 
	 * @param category The category of the stocks to be retrieved.
	 * @return A list of stocks in the specified category.
	 */
	@Override
	public List<Stock> getStocksByCategory(String category) {
		logger.info("Getting stocks by category: {}", category);
		List<Stock> stocks = repository.findByStockCategoryIs(category);
		if (stocks.isEmpty()) {
			logger.error("No stock item under category: {}", category);
			throw new StockNotFoundException("No stock item in this category");
		}
		return stocks;
	}

	/**
	 * Gets stocks by zone ID.
	 * 
	 * @param zoneId The ID of the zone.
	 * @return A response DTO containing stocks and zone information.
	 * @throws ZoneNotFoundException If the zone ID is not found.
	 */
	@Override
	public StockZoneResponseDTO getStocksByZone(int zoneId) throws ZoneNotFoundException {
		try {
			logger.info("Getting stocks by zone ID: {}", zoneId);
			Zone zone = zoneClient.viewZone(zoneId);
			List<Stock> stocks = repository.findByZoneIdIs(zoneId);
			StockZoneResponseDTO responseDTO = new StockZoneResponseDTO(stocks, zone);
			logger.info("Stocks by zone ID retrieved successfully: {}", responseDTO);
			return responseDTO;
		} catch (RuntimeException e) {
			logger.error("Error in stock service: Zone ID not found", e);
			throw new ZoneNotFoundException("Error in stock service: Zone id not found");
		}

	}

	/**
	 * Gets stocks by vendor ID.
	 * 
	 * @param vendorId The ID of the vendor.
	 * @return A response DTO containing stocks and vendor information.
	 * @throws VendorNotFoundException If the vendor ID is not found.
	 */
	@Override
	public StockVendorResponseDTO getStocksByVendor(int vendorId) throws VendorNotFoundException {
		logger.info("Getting stocks by vendor ID: {}", vendorId);
		try {
			Vendor vendor = vendorClient.getVendorById(vendorId);
			List<Stock> stocks = repository.findByVendorIdIs(vendorId);
			StockVendorResponseDTO responseDTO = new StockVendorResponseDTO(stocks, vendor);
			logger.info("Stocks by vendor ID retrieved successfully: {}", responseDTO);
			return responseDTO;
		} catch (RuntimeException e) {
			logger.error("Error in stock service: Vendor ID not found", e);
			throw new VendorNotFoundException("Error in stock service: Vendor ID not found");
		}
	}

	public void triggerStockNotification(Stock stock) {
		logger.info(".......................inside trigger notification");
		logger.info(".......................email sending stock:{}", stock);
		Vendor vendor = vendorClient.getVendorById(stock.getVendorId());
		Zone zone = zoneClient.viewZone(stock.getZoneId());

		// Get admin emails from User service
		List<String> adminEmails = userClient.getAllAdmins();
		logger.info(".......................email sending stock name:{}", stock.getStockName());
		logger.info(".......................email sending stock name:{}", stock.getStockQuantity());

		// Prepare and send admin notification
		AdminNotificationRequest adminRequest = new AdminNotificationRequest(stock.getStockName(),
				stock.getStockQuantity(), vendor.getVendorName(), vendor.getEmail(), zone.getZoneName(), adminEmails);

		notificationClient.sendAdminLowStockNotification(adminRequest);

		// Prepare and send vendor notification
		VendorNotificationRequest vendorRequest = new VendorNotificationRequest(stock.getStockName(),
				vendor.getEmail());
		notificationClient.sendVendorLowStockNotification(vendorRequest);

	}
}
