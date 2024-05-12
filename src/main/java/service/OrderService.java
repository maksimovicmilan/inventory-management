package service;

import com.InventoryManagement.inventorymanagement.Enum.OrderStatus;
import entity.Order;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import repository.OrderRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @CachePut(cacheNames = "orders", key = "#id")
    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    @Cacheable(cacheNames = "orders", key = "#id")
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @CachePut(cacheNames = "orders", key = "#id")
    public Order updateOrder(Long id, Order updatedOrder){
        if(orderRepository.existsById(id)){
            updatedOrder.setId(id);
            return orderRepository.save(updatedOrder);
        }else{
            throw new IllegalArgumentException("Order not found with this id: " + id);
        }
    }

    @CacheEvict(cacheNames = "orders", key = "#id")
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

    public List<Order> getOrderByCustomer(String customer){
        return orderRepository.findByCustomer(customer);
    }

    public List<Order> getOrderByDate(Date dateCreated){
        return orderRepository.findByDate(dateCreated);
    }

    public List<Order> getOrderByKeyword(String keyword){
        return orderRepository.findByKeyword(keyword);
    }

}
