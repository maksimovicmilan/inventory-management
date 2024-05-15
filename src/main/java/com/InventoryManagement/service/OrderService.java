package com.InventoryManagement.service;

import com.InventoryManagement.constant.OrderStatus;
import com.InventoryManagement.entity.Customer;
import com.InventoryManagement.entity.dto.CustomerDto;
import com.InventoryManagement.entity.dto.OrderDto;
import com.InventoryManagement.exception.ApplicationException;
import com.InventoryManagement.exception.BusinessException;
import com.InventoryManagement.repository.CustomerRepository;
import com.InventoryManagement.repository.OrderRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Builder
public class OrderService {

    @Autowired
    private  OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public OrderDto createOrder(OrderDto order) throws BusinessException {
        try {
            if (order.getCustomer() == null) {
                throw new BusinessException("Customer Should not be empty");
            }
            if (order.getDateCreated().isBefore(LocalDate.now())) {
                throw new BusinessException("Date has to be from today");
            }
            if (order.getTotalPrice() <= 0) {
                throw new BusinessException("Total has to be more than '0'");
            }
            CustomerDto customer = new CustomerDto();
            customer.setEmail(order.getCustomer().getEmail());
            customer.setFirstName(order.getCustomer().getFirstName());
            customer.setLastName(order.getCustomer().getLastName());
            customerRepository.saveAndFlush(new CustomerDto());
            orderRepository.saveAndFlush(new OrderDto());
            OrderDto orderDto = new OrderDto();
            orderDto.setOrderNumber(order.getOrderNumber());
            orderDto.setCustomer(order.getCustomer());
            orderDto.setOrderNumber(order.getOrderNumber());
            orderDto.setProduct(order.getProduct());
            orderDto.setStatus(order.getStatus());
            return orderDto;
        } catch (ApplicationException e) {
            e.fillInStackTrace();
        }
        return null;
    }

//    @Cacheable(cacheNames = "orders", key = "#id")
    public List<OrderDto> getAllOrders() throws BusinessException{
        try{
            List<OrderDto> orders = orderRepository.findAll();
            if(orders.isEmpty()){
                throw new BusinessException("You don't have any orders yet, please create an order first");
            }
            return orderRepository.findAll();
        }catch(ApplicationException e){
            e.fillInStackTrace();
        }
        return null;
    }

//    @CachePut(cacheNames = "orders", key = "#id")
    public OrderDto updateOrder(Long id, OrderDto updatedOrder) throws BusinessException {
        try{
            if(!orderRepository.existsById(id)){
                throw new BusinessException("Order not found with id: " + id);
            }
            if(updatedOrder == null || updatedOrder.getCustomer() == null || updatedOrder.getTotalPrice() <= 0){
                throw new BusinessException("Invalid order data");
            }
            orderRepository.findById(id);
            updatedOrder.setId(id);
            return orderRepository.save(updatedOrder);
        } catch (ApplicationException e) {
            e.fillInStackTrace();
        }
        return null;
    }

//    @CacheEvict(cacheNames = "orders", key = "#id")
    public void deleteOrder(Long id) throws BusinessException{
        try{
            if(!orderRepository.existsById(id)){
                throw new BusinessException("Order not found with this id: " + id);
            }
             orderRepository.deleteById(id);
        }catch(ApplicationException e) {
            e.fillInStackTrace();
        }
    }

    public Optional<OrderDto> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    public List<OrderDto> getOrderByOrderNumber(Long orderNumber){
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<OrderDto> getOrderByStatus(OrderStatus status){
        return orderRepository.findByStatus(status);
    }

    public List<OrderDto> getOrderByCustomer(String customerEmail){
        return orderRepository.findByCustomerEmail(customerEmail);
    }

    public List<OrderDto> getOrderByDate(LocalDate dateCreated){
        return orderRepository.findByDateCreated(dateCreated);
    }
}
