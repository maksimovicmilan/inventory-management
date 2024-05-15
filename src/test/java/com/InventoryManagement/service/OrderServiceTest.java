package com.InventoryManagement.service;

import com.InventoryManagement.exception.BusinessException;
import com.InventoryManagement.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

//    @BeforeEach
//    public void setUp() {}

    @Test
    public void testGetAllOrdersWithEmptyList() throws BusinessException {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            orderService.getAllOrders();
        });

        assertEquals("You don't have any orders yet, please create an order first", exception.getMessage());

    }
}
