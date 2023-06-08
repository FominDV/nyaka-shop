package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fomin.nyakashop.entities.OrderItem;
import ru.fomin.nyakashop.enums.OrderStatus;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrder_Id(Long orderId);

    List<OrderItem> findAllByProduct_Id(Long productId);

    List<OrderItem> findAllByProduct_IdAndOrder_User_LoginAndOrder_Status(Long productId, String login, OrderStatus status);

}
