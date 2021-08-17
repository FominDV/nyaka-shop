package ru.fomin.nyakashop.services;

import org.springframework.data.domain.Page;
import ru.fomin.nyakashop.entities.Order;

import java.util.List;

public interface OrderService {

    Long createOrder(String address,String phone);

    Page<Order> findAllByCurrentUser(int pageIndex);

    boolean isOwnedToCurrentUser(Long orderId);

}
