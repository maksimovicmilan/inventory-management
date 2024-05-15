package com.InventoryManagement.service;

import com.InventoryManagement.entity.Product;
import com.InventoryManagement.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.InventoryManagement.repository.ProductRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

//    @BeforeEach
//    public void setUp(){}

    @Test
    void testGetAllProductsWithEmptyList() throws BusinessException {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            productService.getALlProducts();
    });
        assertEquals("You don't have any products yet, please create an product first", exception.getMessage());
    }
}
