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
import ru.fomin.nyakashop.repositories.UserRepository;
import ru.fomin.nyakashop.services.OrderItemService;
import ru.fomin.nyakashop.services.OrderService;
import ru.fomin.nyakashop.util.SecurityUtils;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemServiceImpl implements OrderItemService {

    final OrderService orderService;
    final OrderItemRepository orderItemRepository;
    final UserRepository userRepository;

    @Override
    @Transactional
    public List<OrderItem> findOrderItemsByOrder(Long orderId) {
        if (!orderService.isOwnedToCurrentUser(orderId) && !userRepository.findByLogin(SecurityUtils.getEmail()).get().getRoles().stream().anyMatch(r->r.getRoleName().equals("ROLE_MODERATOR"))) {
            throw new AccessResourceDeniedException(Order.class);
        }
        return orderItemRepository.findAllByOrder_Id(orderId);
    }

}
