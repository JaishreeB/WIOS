package com.cts.wios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.wios.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

	List<Stock> findByStockCategoryIs(String category);

	List<Stock> findByZoneIdIs(int zoneId);

	List<Stock> findByVendorIdIs(int vendorId);

}
