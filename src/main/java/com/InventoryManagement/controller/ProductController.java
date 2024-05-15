package com.InventoryManagement.controller;

import com.InventoryManagement.entity.Product;
import com.InventoryManagement.entity.dto.ProductDto;
import com.InventoryManagement.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product) throws BusinessException {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public List<Product> getAllProducts() throws BusinessException {
        List<Product> products = productService.getALlProducts();
        return products;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDto updatedProduct) throws BusinessException {
        productService.updateProduct(id, updatedProduct);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @GetMapping("/{price}")
    public ResponseEntity<List<Product>> getProductByPrice(@RequestParam Double price) {
        List<Product> products = productService.getProductByPrice(price);
        return ResponseEntity.ok(products);
    }


}
