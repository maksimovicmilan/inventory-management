package com.InventoryManagement.entity.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private double price;
    private Boolean eatable;
    private String flavour;
    private String unitOfMeasurement;
    private long quantity;
}
