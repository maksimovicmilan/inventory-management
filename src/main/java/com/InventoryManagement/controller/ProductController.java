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
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product) throws BusinessException {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAllProducts() throws BusinessException {
        List<ProductDto> products = productService.getALlProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto updatedProduct) throws BusinessException {
        productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
//        try {
//            productService.deleteProduct(id);
//            return ResponseEntity.noContent().build();
//        } catch (ProductNotFoundException ex){
//            return ResponseEntity.notFound().build();
//        } catch(Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws BusinessException {
//        try {
//            Product product = productService.getProductById(id)
//                    .orElseThrow(() -> new BusinessException("Product not found with id: " + id));
//            return ResponseEntity.ok(product);
//        } catch (BusinessException ex) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/{flavor}")
    public ResponseEntity<List<ProductDto>> getProductsByFlavor(@RequestParam String flavor) {
        List<ProductDto> products = productService.getProductsByFlavor(flavor);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{price}")
    public ResponseEntity<List<ProductDto>> getProductByPrice(@RequestParam Double price) {
        List<ProductDto> products = productService.getProductByPrice(price);
        return ResponseEntity.ok(products);
    }


}
