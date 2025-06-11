package com.cts.wios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vendor {
	@Id
	
//	@Positive(message="Id shouldn't be zero or negative value")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vendorId;
	@NotBlank(message="Vendor name can't be null or white space character")
	@Size(min=3,max=15)
	private String vendorName;
	@Column(name = "mobile_number", nullable = false, unique = true)
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String contactInfo;
	@Email(message = "Invalid email format")
    private String email;
}
