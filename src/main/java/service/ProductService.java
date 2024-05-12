package service;

import entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import repository.ProductRepository;
import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @CachePut(cacheNames = "products", key = "#id")
    public Product createProduct(Product product){
        if(product.getName() == null ||product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        } else if(product.getPrice() <= 0){
            throw new IllegalArgumentException("Price must be greater than 0");
        }else {
            return productRepository.save(product);
        }
    }

    @Cacheable(cacheNames = "products", key = "#id")
    public List<Product> getALlProducts(){
        return productRepository.findAll();
    }

    @CachePut(cacheNames = "products", key = "#id")
    public Product updateProduct(Long id, Product updatedProduct){
        if(productRepository.existsById(id)) {
            updatedProduct.setId(id);
            return productRepository.save(updatedProduct);
        }else{
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }

    @CacheEvict(cacheNames = "products", key = "#id")
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
        return productRepository.findByFlavor(flavor);
    }

    public List<Product>getProductByPrice(Double price){
        return productRepository.findByPrice(price);
    }

    public List<Product>getByKeywordContainingIgnoreCase(String keyword){
        return productRepository.findByKeywordContainingIgnoreCase(keyword);
    }

}
