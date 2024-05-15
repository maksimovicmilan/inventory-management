package com.InventoryManagement.service;

import com.InventoryManagement.entity.dto.ProductDto;
import com.InventoryManagement.exception.ApplicationException;
import com.InventoryManagement.exception.BusinessException;
import com.InventoryManagement.repository.ProductRepository;
import com.InventoryManagement.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


//    @CachePut(cacheNames = "products", key = "#id")
    public Product createProduct(Product product) throws BusinessException{
        try{
            if(product.getName() == null ||product.getName().isEmpty()) {
                throw new BusinessException("Product name cannot be empty");
            }
            if(product.getPrice() <= 0){
            throw new BusinessException("Price must be greater than 0");
            }

            return productRepository.save(product);
        }catch(ApplicationException e){
            e.fillInStackTrace();
        }
        return null;
    }

    //@Cacheable(cacheNames = "products", key = "#id")
    public List<Product> getALlProducts() throws BusinessException {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                throw new BusinessException("You don't have any products yet, please create an product first");
            }
            return productRepository.findAll();
        } catch (ApplicationException e) {
            e.fillInStackTrace();
        }
        return null;
    }

    //@CachePut(cacheNames = "products", key = "#id")
    public Product updateProduct(Long id, ProductDto updatedProduct) throws BusinessException {
        try {
            Optional<Product> product = productRepository.findById(id);
            if(product.isEmpty()){
                throw new BusinessException("Product not found with id: " + id);
            }
            Product existingProduct = product.get();
            if(updatedProduct.getName() != null){
                existingProduct.setName(updatedProduct.getName());
            }
            if(updatedProduct.getPrice() != 0.0){
                existingProduct.setPrice(updatedProduct.getPrice());
            }
            if(updatedProduct.getFlavour() != null){
                existingProduct.setFlavour(updatedProduct.getFlavour());
            }
            if(updatedProduct.getEatable() != null){
                existingProduct.setEatable(updatedProduct.getEatable());
            }
            if(updatedProduct.getUnitOfMeasurement() != null){
                existingProduct.setUnitOfMeasurement(updatedProduct.getUnitOfMeasurement());
            }
                existingProduct.setQuantity((updatedProduct.getQuantity()));
            return productRepository.save(existingProduct);
        } catch (ApplicationException ex) {
            ex.fillInStackTrace();
        }
        return null;
    }

   // @CacheEvict(cacheNames = "products", key = "#id")
    public void deleteProduct(Long id){
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product>getProductsByFlavor(String flavor){
        return productRepository.findByFlavour(flavor);
    }

    public List<Product>getProductByPrice(Double price){
        return productRepository.findByPrice(price);
    }

}
