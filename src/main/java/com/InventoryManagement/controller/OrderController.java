package com.InventoryManagement.controller;

import com.InventoryManagement.entity.Customer;
import com.InventoryManagement.entity.dto.OrderDto;
import com.InventoryManagement.exception.BusinessException;
import com.InventoryManagement.constant.OrderStatus;
import com.InventoryManagement.service.OrderService;
import com.InventoryManagement.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<Order> getAllOrders() throws BusinessException {
        List<Order> orders = orderService.getAllOrders();

        return orders;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto updatedOrder) throws BusinessException{
             orderService.updateOrder(id, updatedOrder);
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

//    @GetMapping("/{id}")
//    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) throws BusinessException{
//        try{
//            OrderDto order = orderService.getOrderById(id)
//                    .orElseThrow(() -> new BusinessException("Order not found with id: " + id));
//            return ResponseEntity.ok(order);
//        }catch(IllegalArgumentException ex){
//            return ResponseEntity.notFound().build();
//        }catch(Exception ex){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/{orderNumber}")
    public OrderDto getOrderByOrderNumber(@RequestParam long orderNumber){
        Order orders = orderService.getOrderByOrderNumber(orderNumber);
        return new OrderDto(orders.getOrderNumber(),
                orders.getTotalPrice(),
                null,
                orders.getDateCreated(),
                orders.getCustomer(),
                orders.getProduct());
    }

    @GetMapping("/{customer}")
    public List<Order> getOrderByCustomer(@RequestParam Customer customer){
        List<Order> orders = orderService.getOrderByCustomerEmail(customer.getEmail());
        return orders;
    }

//    @GetMapping("/{date}")
//    public ResponseEntity<List<OrderDto>> getOrderByDate(@RequestParam LocalDate dateCreated){
//        List<OrderDto> orders = orderService.getOrderByDate(dateCreated);
//        return ResponseEntity.ok(orders);
//    }


//    @GetMapping("/{keyword}")
//    public ResponseEntity<List<OrderDto>> getOrderByKeyword(@RequestParam String keyword){
//        List<OrderDto> orders = orderService.getOrderByKeyword(keyword);
//        return ResponseEntity.ok(orders);
//    }

}
