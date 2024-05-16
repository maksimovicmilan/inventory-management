package com.InventoryManagement.controller;

import com.InventoryManagement.entity.Product;
import com.InventoryManagement.entity.dto.ProductDto;
import com.InventoryManagement.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.InventoryManagement.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws BusinessException {
        productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/")
    public List<Product> getAllProducts() throws BusinessException {
        List<Product> products = productService.getAllProducts();
        return products;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) throws BusinessException {
        productService.updateProduct(id, updatedProduct);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws BusinessException{
        productService.deleteProduct(id);
        return(ResponseEntity<?>) ResponseEntity.ok();
    }

    @GetMapping("/{price}")
    public ResponseEntity<List<Product>> getProductByPrice(@RequestParam Double price) {
        List<Product> products = productService.getProductByPrice(price);
        return ResponseEntity.ok(products);
    }


}
