package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.dto.Order;
import java.util.List;
public interface OrderService {

    Long createOrder();

    List<Order> getOrderList();

    Order getOrder(Long orderId);

}
