package com.cts.wios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
	@Positive(message="Id shouldn't be zero or negative value")
	private int zoneId;
	@NotBlank
	@Size(min = 3, max = 20,message="zone name should be in the range of 3-20")
	private String zoneName;
	private int zoneCapacity;
	private int availableSpace;

	public int getOccupiedSpace() {
		return zoneCapacity - availableSpace;
	}

}
