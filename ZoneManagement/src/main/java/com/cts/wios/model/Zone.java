package com.cts.wios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
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
	@Positive(message = "Id shouldn't be zero or negative value")
	private int zoneId;

	@NotBlank(message = "Zone name must not be blank")
	@Size(min = 3, max = 20, message = "Zone name should be in the range of 3-20 characters")
	private String zoneName;

	@Min(value = 1, message = "Zone capacity must be at least 1")
	private int zoneCapacity;

	@Min(value = 0, message = "Available space cannot be negative")
	private int availableSpace;

	public int getOccupiedSpace() {
		return zoneCapacity - availableSpace;
	}

	@AssertTrue(message = "Available space cannot exceed zone capacity")
	public boolean isAvailableSpaceValid() {
		return availableSpace <= zoneCapacity;
	}

}
