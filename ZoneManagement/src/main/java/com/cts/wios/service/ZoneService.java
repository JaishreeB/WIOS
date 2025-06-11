package com.cts.wios.service;

import java.util.List;

import com.cts.wios.exceptions.ZoneNotFound;
import com.cts.wios.model.Zone;

public interface ZoneService {
	public String createZone(Zone zone);

	public Zone updateZone(Zone zone);

	public Zone viewZone(int warehouseZoneId) throws ZoneNotFound;

	public String deleteZone(int warehouseZoneId);
	
	public List<Zone> getAllZone();

	public String getZoneNameById(int warehouseZoneId) throws ZoneNotFound;
}
