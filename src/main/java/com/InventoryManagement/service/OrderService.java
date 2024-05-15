package com.InventoryManagement.service;

import com.InventoryManagement.constant.OrderStatus;
import com.InventoryManagement.entity.Customer;
import com.InventoryManagement.entity.Order;
import com.InventoryManagement.entity.dto.OrderDto;
import com.InventoryManagement.exception.ApplicationException;
import com.InventoryManagement.exception.BusinessException;
import com.InventoryManagement.repository.CustomerRepository;
import com.InventoryManagement.repository.OrderRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> createOrder(OrderDto order) throws BusinessException {
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
            Customer customer = new Customer();
            customer.setEmail(order.getCustomer().getEmail());
            customer.setFirstName(order.getCustomer().getFirstName());
            customer.setLastName(order.getCustomer().getLastName());
            customerRepository.saveAndFlush(customer);

            Order newOrder = new Order();
            newOrder.setOrderNumber(order.getOrderNumber());
            newOrder.setCustomer(order.getCustomer());
            newOrder.setOrderNumber(order.getOrderNumber());
            newOrder.setProduct(order.getProduct());
            orderRepository.saveAndFlush(newOrder);


            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ApplicationException e) {
            e.fillInStackTrace();
        }
        return null;
    }

//    @Cacheable(cacheNames = "orders", key = "#id")
    public List<Order> getAllOrders() throws BusinessException{
        try{
            List<Order> orders = orderRepository.findAll();
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
    public ResponseEntity<?> updateOrder(Long id, OrderDto updatedOrder) throws BusinessException {
        try{
            if(!orderRepository.existsById(id)){
                throw new BusinessException("Order not found with id: " + id);
            }
            if(updatedOrder == null || updatedOrder.getCustomer() == null || updatedOrder.getTotalPrice() <= 0){
                throw new BusinessException("Invalid order data");
            }
            Optional<Order> order = orderRepository.findById(id);
            orderRepository.delete(order.get());

            Order newOrder = new Order();
            newOrder.setTotalPrice(updatedOrder.getTotalPrice());
            newOrder.setOrderNumber(updatedOrder.getOrderNumber());
            newOrder.setDateCreated(updatedOrder.getDateCreated());
            newOrder.setProduct(updatedOrder.getProduct());
            newOrder.setCustomer(updatedOrder.getCustomer());
            orderRepository.save(newOrder);


            return new ResponseEntity<>(HttpStatus.ACCEPTED);

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

    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    public Order getOrderByOrderNumber(Long orderNumber){
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<Order> getOrderByCustomerEmail(String customerEmail){
        return orderRepository.findByCustomerEmail(customerEmail);
    }

//    public List<OrderDto> getOrderByDate(LocalDate dateCreated){
//        return orderRepository.findByDateCreated(dateCreated);
//    }
}
