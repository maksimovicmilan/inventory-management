package com.InventoryManagement.repository;

import com.InventoryManagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByFlavour(String flavor);

    List<Product> findByPrice(double price);

//    List<Product> findByKeywordContainingIgnoreCase(String keyword);

}
