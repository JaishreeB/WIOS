package com.cts.wios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.wios.model.PerformanceMetrics;

public interface PerformanceMetricsRepository extends JpaRepository<PerformanceMetrics, Integer> {

	List<PerformanceMetrics> findByTypeIs(String type);

}
