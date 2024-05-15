package com.InventoryManagement.controller;

import com.InventoryManagement.entity.Customer;
import com.InventoryManagement.entity.dto.OrderDto;
import com.InventoryManagement.exception.BusinessException;
import com.InventoryManagement.constant.OrderStatus;
import com.InventoryManagement.service.OrderService;
import com.InventoryManagement.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto order) throws BusinessException {
            orderService.createOrder(order);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> getAllOrders() throws BusinessException {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto updatedOrder) throws BusinessException{
            OrderDto order = orderService.updateOrder(id, updatedOrder);
            return ResponseEntity.ok(updatedOrder);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id){
        try{
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        }catch(IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) throws BusinessException{
        try{
            OrderDto order = orderService.getOrderById(id)
                    .orElseThrow(() -> new BusinessException("Order not found with id: " + id));
            return ResponseEntity.ok(order);
        }catch(IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<List<OrderDto>> getOrderByOrderNumber(@RequestParam long orderNumber){
        List<OrderDto> orders = orderService.getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{customer}")
    public ResponseEntity<List<OrderDto>> getOrderByCustomer(@RequestParam Customer customer){
        List<OrderDto> orders = orderService.getOrderByCustomer(customer.getEmail());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<OrderDto>> getOrderByDate(@RequestParam LocalDate dateCreated){
        List<OrderDto> orders = orderService.getOrderByDate(dateCreated);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<OrderDto>> getOrderByStatus(@RequestParam OrderStatus status){
        List<OrderDto> orders = orderService.getOrderByStatus(status);
        return ResponseEntity.ok(orders);
    }

//    @GetMapping("/{keyword}")
//    public ResponseEntity<List<OrderDto>> getOrderByKeyword(@RequestParam String keyword){
//        List<OrderDto> orders = orderService.getOrderByKeyword(keyword);
//        return ResponseEntity.ok(orders);
//    }

}
