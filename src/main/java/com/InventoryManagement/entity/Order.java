package com.InventoryManagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ordersFromCustomer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "orderNumber")
    private long orderNumber;
    @Column(name = "totalPrice")
    private double totalPrice;

//    private OrderStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
}
