package com.cts.wios.service;

import java.util.List;

import com.cts.wios.model.Zone;

public interface ZoneService {
	public String createZone(Zone zone);

	public Zone updateZone(Zone zone);

	public Zone viewZone(int warehouseZoneId);

	public String deleteZone(int warehouseZoneId);
	
	public List<Zone> getAllZone();
}
