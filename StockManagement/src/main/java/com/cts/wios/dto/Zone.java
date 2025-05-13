package com.cts.wios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zone {
	private int zoneId;
	private String zoneName;
	private int zoneCapacity;
	private int availableSpace;
	public int getOccupiedSpace() {
		return zoneCapacity - availableSpace;
	}
}
