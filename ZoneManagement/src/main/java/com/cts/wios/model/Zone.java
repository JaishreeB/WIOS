package com.cts.wios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WarehouseZone_info")
public class Zone {
	@Id
	int zoneId;
	String zoneName;
	int zoneCapacity;

}
