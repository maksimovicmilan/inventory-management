package com.InventoryManagement.repository;

import com.InventoryManagement.entity.Product;
import com.InventoryManagement.entity.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<ProductDto, Long> {

    List<ProductDto> findByFlavour(String flavor);

    List<ProductDto> findByPrice(double price);

}
