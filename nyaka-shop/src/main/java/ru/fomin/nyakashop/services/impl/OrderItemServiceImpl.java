package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fomin.nyakashop.entities.Order;
import ru.fomin.nyakashop.entities.OrderItem;
import ru.fomin.nyakashop.exceptions.AccessResourceDeniedException;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.repositories.OrderItemRepository;
import ru.fomin.nyakashop.repositories.OrderRepository;
import ru.fomin.nyakashop.services.OrderItemService;
import ru.fomin.nyakashop.services.OrderService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemServiceImpl implements OrderItemService {

    final OrderService orderService;
    final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public List<OrderItem> findOrderItemsByOrder(Long orderId) {
        if (!orderService.isOwnedToCurrentUser(orderId)) {
            throw new AccessResourceDeniedException(Order.class);
        }
        return orderItemRepository.findAllByOrder_Id(orderId);
    }

}
