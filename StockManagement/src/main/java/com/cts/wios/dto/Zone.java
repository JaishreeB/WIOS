package com.cts.wios.dto;



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
@Table(name = "zone_info")
public class Zone {
	@Id
	private int zoneId;
	private String zoneName;
	private int zoneCapacity;
	private int availableSpace;
}
