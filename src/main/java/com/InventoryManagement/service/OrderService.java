package com.InventoryManagement.service;

import com.InventoryManagement.constant.OrderStatus;
import com.InventoryManagement.entity.Customer;
import com.InventoryManagement.entity.dto.OrderDto;
import com.InventoryManagement.exception.ApplicationException;
import com.InventoryManagement.exception.BusinessException;
import com.InventoryManagement.repository.CustomerRepository;
import com.InventoryManagement.repository.OrderRepository;
import com.InventoryManagement.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private  OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public OrderDto createOrder(Order order) throws BusinessException {
        try {
            if (order.getCustomer() == null) {
                throw new BusinessException("Customer Should not be empty");}
            if (order.getDateCreated().isBefore(LocalDate.now().minusDays(1))) {
                throw new BusinessException("Can't create an order before one day");}
            if (order.getTotalPrice() <= 0) {
                throw new BusinessException("Can't create the order");}
            Customer customer = new Customer();
            customer.setEmail(order.getCustomer().getEmail());
            customer.setFirstName(order.getCustomer().getFirstName());
            customer.setLastName(order.getCustomer().getLastName());
            customerRepository.saveAndFlush(customer);
            orderRepository.saveAndFlush(order);
            OrderDto orderDto = new OrderDto();
            orderDto.setOrderNumber(order.getOrderNumber());
//            orderDto.setProduct(order.getProduct());
            orderDto.setCustomer(order.getCustomer());
            orderDto.setOrderNumber(order.getOrderNumber());
//            orderDto.setStatus(order.getStatus());
            return orderDto;
        } catch (ApplicationException e) {
             e.fillInStackTrace();
        }
        return null;
    }

//    @Cacheable(cacheNames = "orders", key = "#id")
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

//    @CachePut(cacheNames = "orders", key = "#id")
    public Order updateOrder(Long id, Order updatedOrder){
        if(orderRepository.existsById(id)){
            updatedOrder.setId(id);
            return orderRepository.save(updatedOrder);
        }else{
            throw new IllegalArgumentException("Order not found with this id: " + id);
        }
    }

//    @CacheEvict(cacheNames = "orders", key = "#id")
    public void deleteOrder(Long id){
        if(orderRepository.existsById(id)){
            orderRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("Order not found with this id: " + id);
        }
    }

    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    public List<Order> getOrderByOrderNumber(Long orderNumber){
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<Order> getOrderByStatus(OrderStatus status){
        return orderRepository.findByStatus(status);
    }

    public List<Order> getOrderByCustomer(String customerEmail){
        return orderRepository.findByCustomerEmail(customerEmail);
    }

    public List<Order> getOrderByDate(LocalDate dateCreated){
        return orderRepository.findByDateCreated(dateCreated);
    }

//    public List<Order> getOrderByKeyword(String keyword){
//        return orderRepository.findByKeyword(keyword);
//    }

}
