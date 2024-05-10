package service;

import entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Product> getALlProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product>getProductsByFlavor(String flavour){
        return productRepository.findByFlavor(flavour);
    }

    public List<Product>getProductByPrice(Double price){
        return productRepository.findByPrice(price);
    }

    public List<Product>getByKeywordContainingIgnoreCase(String keyword){
        return productRepository.findByKeywordContainingIgnoreCase(keyword);
    }
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct){
        if(productRepository.existsById(id)) {
            updatedProduct.setId(id);
            return productRepository.save(updatedProduct);
        }else{
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }
    public void deleteProduct(Long id){
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }


}
