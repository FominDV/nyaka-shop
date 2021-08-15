package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fomin.nyakashop.beans.Cart;
import ru.fomin.nyakashop.entities.Order;
import ru.fomin.nyakashop.entities.OrderItem;
import ru.fomin.nyakashop.mappers.Mapper;
import ru.fomin.nyakashop.repositories.OrderRepository;
import ru.fomin.nyakashop.services.OrderService;
import ru.fomin.nyakashop.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {

    final Cart cart;
    final OrderRepository orderRepository;
    final UserService userService;

    @Override
    @Transactional
    public Long createOrder(String address, String phone) {
        List<OrderItem> orderItemList = cart.getItems().stream()
                .map(Mapper.INSTANCE::toOrderItem)
                .collect(Collectors.toList());
        Order order = Order.builder()
                .user(userService.findCurrentUser())
                .items(orderItemList)
                .totalPrice(cart.getTotalPrice())
                .address(address)
                .phone(phone)
                .build();
        orderItemList.forEach(orderItem -> orderItem.setOrder(order));
        Long orderId = orderRepository.save(order).getId();
        cart.clearCart();
        return orderId;
    }
}
