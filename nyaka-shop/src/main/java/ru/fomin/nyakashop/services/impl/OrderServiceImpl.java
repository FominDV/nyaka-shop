package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fomin.nyakashop.beans.Cart;
import ru.fomin.nyakashop.dto.OrderDto;
import ru.fomin.nyakashop.entities.Order;
import ru.fomin.nyakashop.entities.OrderItemEn;
import ru.fomin.nyakashop.repositories.OrderRepository;
import ru.fomin.nyakashop.services.OrderItemService;
import ru.fomin.nyakashop.services.OrderService;
import ru.fomin.nyakashop.services.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {

    final Cart cart;
    final OrderItemService orderItemService;
    final OrderRepository orderRepository;
    final UserService userService;

    @Override
    @Transactional
    public Long createOrder() {
        if (cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        Order orderEn = Order.builder()
                .user(userService.findCurrentUser())
                .totalPrice(cart.getTotalPrice())
                .build();
        List<OrderItemEn> orderItemEnList = orderItemService.create(cart.getOrderItemDtoList(), orderEn);
        orderEn.setItems(orderItemEnList);
        cart.clearCart();
        return orderRepository.save(orderEn).getId();
    }

    @Override
    @Transactional
    public List<OrderDto> getOrderList() {

        return null;
    }

    @Override
    @Transactional
    public OrderDto getOrder(Long orderId) {

        return null;
    }

}
