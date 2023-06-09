package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fomin.nyakashop.entities.Order;
import ru.fomin.nyakashop.entities.OrderItem;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.mappers.UniversalMapper;
import ru.fomin.nyakashop.mappers.impl.OrderItemDtoMapper;
import ru.fomin.nyakashop.repositories.OrderRepository;
import ru.fomin.nyakashop.services.CartService;
import ru.fomin.nyakashop.services.OrderService;
import ru.fomin.nyakashop.services.UserService;
import ru.fomin.nyakashop.util.Cart;
import ru.fomin.nyakashop.util.SecurityUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {

    @Value("${pageSize.order}")
    Integer pageSize;

    final CartService cartService;
    final OrderRepository orderRepository;
    final UserService userService;
    final UniversalMapper universalMapper;
    final OrderItemDtoMapper orderItemDtoMapper;

    @Override
    @Transactional
    public Long createOrder(String address, String phone) {
        Cart cart = cartService.getCart();
        List<OrderItem> orderItemList = orderItemDtoMapper.convert(cart.getItems());
        Order order = Order.builder()
                .user(userService.findCurrentUser())
                .items(orderItemList)
                .totalPrice(cart.getTotalPrice())
                .address(address)
                .phone(phone)
                .build();
        orderItemList.forEach(orderItem -> orderItem.setOrder(order));
        Long orderId = orderRepository.save(order).getId();
        cartService.clearCart();
        return orderId;
    }

    @Override
    public Page<Order> findAllByCurrentUser(int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdAt").descending());
        return orderRepository.findAllByUser_Login(SecurityUtils.getEmail(), pageable);
    }

    @Override
    public boolean isOwnedToCurrentUser(Long orderId) {
        return orderRepository.existsOrderByIdAndUser_Login(orderId, SecurityUtils.getEmail());
    }

    @Override
    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order getOrderOrThrow(Long id) {
        return getOrder(id)
                .orElseThrow(() -> new ResourceNotFoundException(Order.class));
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

}
