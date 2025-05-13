package com.cts.wios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_info")
public class Stock {
    @Id
    @Positive(message="Stock Id should be positive")
    private int stockId;

    @NotBlank(message = "Stock name cannot be blank")
    @Size(min = 2, max = 50, message = "Stock name must be between 2 and 50 characters")
    private String stockName;

    @NotBlank(message = "Stock category cannot be blank")
    @Size(min = 2, max = 50, message = "Stock category must be between 2 and 50 characters")
    private String stockCategory;

    @Min(value = 0, message = "Stock quantity must be a positive number")
    private int stockQuantity;

    @NotNull(message = "Zone ID cannot be null")
    @Positive(message="Zone Id should be positive")
    private int zoneId;

    @NotNull(message = "Vendor ID cannot be null")
    @Positive(message="Vendor Id should be positive")
    private int vendorId;
}
