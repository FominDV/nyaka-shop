package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.entities.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> findOrderItemsByOrder(Long orderId);

}
