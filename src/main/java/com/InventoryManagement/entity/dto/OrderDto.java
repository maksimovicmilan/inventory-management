package com.InventoryManagement.entity.dto;

import com.InventoryManagement.constant.OrderStatus;
import com.InventoryManagement.entity.Customer;
import com.InventoryManagement.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private long orderNumber;
    private double totalPrice;
    private OrderStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;
    private Customer customer;
    private Product product;

}
