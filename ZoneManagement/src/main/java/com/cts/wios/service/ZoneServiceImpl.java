package com.cts.wios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.wios.model.Zone;
import com.cts.wios.repository.ZoneRepository;

@Service
public class ZoneServiceImpl implements ZoneService {
	@Autowired
	ZoneRepository repository;

	@Override
	public String createZone(Zone zone) {
		repository.save(zone);
		return "Zone saved successfully";
	}

	@Override
	public Zone updateZone(Zone zone) {
		return repository.save(zone);
	}

	@Override
	public Zone viewZone(int warehouseZoneId) {
		Optional<Zone> optional = repository.findById(warehouseZoneId);
//		if (optional.isPresent())
//			return optional.get();
//		else
//			throw new ZoneNotFound("Invalid Id");
		return optional.get();
	}

	@Override
	public String deleteZone(int warehouseZoneId) {
		repository.deleteById(warehouseZoneId);
		return "Zone deleted";
	}

	@Override
	public List<Zone> getAllZone() {
		return repository.findAll();
	}

}
