package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.dto.OrderDto;
import java.util.List;
public interface OrderService {

    Long createOrder();

    List<OrderDto> getOrderList();

    OrderDto getOrder(Long orderId);

}
