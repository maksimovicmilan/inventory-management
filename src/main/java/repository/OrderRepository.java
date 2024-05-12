package repository;

import com.InventoryManagement.inventorymanagement.Enum.OrderStatus;
import entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderNumber(Long orderNumber);

    List<Order> findByCustomer(String Customer);

    List<Order> findByDate(Date dateCreated);

    List<Order> findByStatus(OrderStatus Status);

    List<Order> findByKeyword(String Keyword);
}
