package com.InventoryManagement.repository;

import com.InventoryManagement.entity.Customer;
import com.InventoryManagement.entity.Product;
import com.InventoryManagement.entity.dto.CustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFirstName(String firstName);

}
