package com.cts.wios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
//	@SuppressWarnings("unused")
	private int vendorId;
//	@SuppressWarnings("unused")
	private String vendorName;
//	@SuppressWarnings("unused")
	private long contactInfo;
	private String email;
}
