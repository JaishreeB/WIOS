package com.cts.wios.service;

import java.util.List;

import com.cts.wios.model.PerformanceMetrics;

public interface PerformanceMetricsService {
	public void calculateAndSaveMetrics() ;

	public List<PerformanceMetrics> findByType(String type);

}
