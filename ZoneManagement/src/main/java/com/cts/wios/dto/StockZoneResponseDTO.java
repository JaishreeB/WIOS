package com.cts.wios.dto;

import java.util.List;

import com.cts.wios.model.Zone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockZoneResponseDTO {
	private List<Stock> stock;
	private Zone zone;
}
