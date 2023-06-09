package ru.fomin.nyakashop.services;

import org.springframework.data.domain.Page;
import ru.fomin.nyakashop.entities.Order;
import ru.fomin.nyakashop.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Long createOrder(String address,String phone);

    Page<Order> findAllByCurrentUser(int pageIndex);

    Page<Order> findAllByStatus(int pageIndex,List<OrderStatus> statuses);

    boolean isOwnedToCurrentUser(Long orderId);

    Optional<Order> getOrder(Long id);

    Order getOrderOrThrow(Long id);

    Order update(Order order);

}
