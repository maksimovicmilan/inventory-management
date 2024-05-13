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
    public ResponseEntity<OrderDto> createOrder(@RequestBody Order order) throws BusinessException {
            orderService.createOrder(order);
            return ResponseEntity.ok().build();
    }
//        try{
//            Order updatedOrder = orderService.createOrder(order);
//            return ResponseEntity.status(HttpStatus.CREATED).body(updatedOrder);
//        }catch(Exception ex){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder){
        try{
            Order order = orderService.updateOrder(id, updatedOrder);
            return ResponseEntity.ok(updatedOrder);
        }catch (IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        try{
            Order order = orderService.getOrderById(id)
                    .orElseThrow(() -> new BusinessException("Order not found with id: " + id));
            return ResponseEntity.ok(order);
        }catch(IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<List<Order>> getOrderByOrderNumber(@RequestParam long orderNumber){
        List<Order> orders = orderService.getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{customer}")
    public ResponseEntity<List<Order>> getOrderByCustomer(@RequestParam Customer customer){
        List<Order> orders = orderService.getOrderByCustomer(customer.getEmail());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<Order>> getOrderByDate(@RequestParam LocalDate dateCreated){
        List<Order> orders = orderService.getOrderByDate(dateCreated);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<Order>> getOrderByStatus(@RequestParam OrderStatus status){
        List<Order> orders = orderService.getOrderByStatus(status);
        return ResponseEntity.ok(orders);
    }

//    @GetMapping("/{keyword}")
//    public ResponseEntity<List<Order>> getOrderByKeyword(@RequestParam String keyword){
//        List<Order> orders = orderService.getOrderByKeyword(keyword);
//        return ResponseEntity.ok(orders);
//    }

}