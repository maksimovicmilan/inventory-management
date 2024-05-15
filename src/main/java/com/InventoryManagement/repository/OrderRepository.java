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
public interface OrderRepository extends JpaRepository<OrderDto, Long> {

    List<OrderDto> findByOrderNumber(Long orderNumber);

    List<OrderDto> findByCustomerEmail(String customer);

    List<OrderDto> findByDateCreated(LocalDate dateCreated);

    List<OrderDto> findByStatus(OrderStatus Status);

//    List<OrderDto> findByKeyword(String Keyword);
}
