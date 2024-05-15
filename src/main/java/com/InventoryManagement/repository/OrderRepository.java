package com.InventoryManagement.repository;

import com.InventoryManagement.constant.OrderStatus;
import com.InventoryManagement.entity.Customer;
import com.InventoryManagement.entity.Order;
import com.InventoryManagement.entity.dto.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderNumber(Long orderNumber);

    List<Order> findByCustomerEmail(String customer);

//    List<Order> findByStatus(OrderStatus status);

//    List<Order> findByDateCreated(LocalDate dateCreated);

//    List<Order> findByStatus(OrderStatus Status);

//    List<OrderDto> findByKeyword(String Keyword);
}
