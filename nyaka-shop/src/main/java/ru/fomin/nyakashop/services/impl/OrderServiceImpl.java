package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fomin.nyakashop.beans.Cart;
import ru.fomin.nyakashop.dto.Order;
import ru.fomin.nyakashop.entities.OrderEn;
import ru.fomin.nyakashop.entities.OrderItemEn;
import ru.fomin.nyakashop.mappers.OrderMapper;
import ru.fomin.nyakashop.repositories.OrderRepository;
import ru.fomin.nyakashop.services.OrderItemService;
import ru.fomin.nyakashop.services.OrderService;
import ru.fomin.nyakashop.services.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {

    @Resource
    Cart cart;

    @Resource
    OrderItemService orderItemService;

    @Resource
    OrderRepository orderRepository;

    @Resource
    UserService userService;

    @Resource
    OrderMapper orderMapper;

    @Override
    @Transactional
    public Long createOrder() {
        if (cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        OrderEn orderEn =OrderEn.builder()
                .user(userService.findCurrentUser())
                .totalPrice(cart.getTotalPrice())
                .build();
        List<OrderItemEn> orderItemEnList = orderItemService.create(cart.getOrderItemList(), orderEn);
        orderEn.setItems(orderItemEnList);
        cart.clearCart();
        return orderRepository.save(orderEn).getId();
    }

    @Override
    @Transactional
    public List<Order> getOrderList() {
        return orderMapper.convertToOrderList(orderRepository.findAllByUser_EmailOrderByCreatedAtDesc("ddddddd"));
    }

    @Override
    @Transactional
    public Order getOrder(Long orderId) {
        OrderEn orderEn = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order was not found"));
        //verifyAccess(orderEn);
        return orderMapper.convertToOrder(orderEn);
    }

//    @SneakyThrows
//    private void verifyAccess(OrderEn orderEn) {
//        if(!SecurityUtil.getEmail().equals(orderEn.getUser().getEmail())){
//            throw new AccessDeniedException("access denied");
//        }
//    }

}
