package com.cts.wios.cotroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.wios.model.PerformanceMetrics;
import com.cts.wios.service.PerformanceMetricsService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/metrics")
@AllArgsConstructor
public class PerformanceMetricsController {
	
	@Autowired
	PerformanceMetricsService service;
	
	@GetMapping("/bytype/{stype}")
	public List<PerformanceMetrics> getMetrics(@PathVariable("stype") String type) {
		return service.findByType(type);
	}

	@GetMapping("/calmetrics")
	public String calculateAndSaveMetrics() {
		service.calculateAndSaveMetrics();
		return "Metrics Generated";
	}
	
}
