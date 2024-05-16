package com.InventoryManagement.service;

import com.InventoryManagement.entity.Order;
import com.InventoryManagement.exception.ApplicationException;
import com.InventoryManagement.exception.BusinessException;
import com.InventoryManagement.repository.ProductRepository;
import com.InventoryManagement.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Product> createProduct(Product product) throws BusinessException{
        try{
            if(product.getName() == null ||product.getName().isEmpty()) {
                throw new BusinessException("Product name cannot be empty");
            }
            if(product.getPrice() <= 0){
            throw new BusinessException("Price must be greater than 0");
            }
            Product newProduct = new Product();
            newProduct.setPrice(product.getPrice());
            newProduct.setName(product.getName());
            newProduct.setUnitOfMeasurement(product.getUnitOfMeasurement());
            newProduct.setEatable(product.getEatable());
            newProduct.setQuantity(product.getQuantity());
            newProduct.setFlavour(product.getFlavour());
            productRepository.saveAndFlush(newProduct);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ApplicationException e) {
            e.fillInStackTrace();
        }
        return null;
    }

    //@Cacheable(cacheNames = "products", key = "#id")
    public List<Product> getAllProducts() throws BusinessException {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                throw new BusinessException("You don't have any products yet, please create a product first");
            }
            return products;
        } catch (ApplicationException e) {
            e.fillInStackTrace();
        }
        return null;
    }

    //@CachePut(cacheNames = "products", key = "#id")
    public ResponseEntity<Product> updateProduct(Long id, Product updatedProduct) throws BusinessException {
        try{
            if(!productRepository.existsById(id))
                throw new BusinessException("Order not found with id: " + id);

            if(updatedProduct == null || updatedProduct.getName() == null || updatedProduct.getPrice() <= 0)
                throw new BusinessException("Invalid order data");

            Optional<Product> product = productRepository.findById(id);
            productRepository.delete(product.get());
            Product newProduct = new Product();
            newProduct.setName(updatedProduct.getName());
            newProduct.setPrice(updatedProduct.getPrice());
            newProduct.setEatable(updatedProduct.getEatable());
            newProduct.setFlavour(updatedProduct.getFlavour());
            newProduct.setQuantity(updatedProduct.getQuantity());
            productRepository.save(newProduct);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ApplicationException e) {
            e.fillInStackTrace();
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
        List<Product> products = productRepository.findByFlavour(flavor);
        return products;
    }

    public List<Product>getProductByPrice(Double price){
        List<Product> products = productRepository.findByPrice(price);
        return products;
    }

}
