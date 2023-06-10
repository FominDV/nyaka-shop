package ru.fomin.nyakashop.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.mappers.UniversalMapper;
import ru.fomin.nyakashop.mappers.impl.OrderItemDtoMapper;
import ru.fomin.nyakashop.services.OrderItemService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders/{orderId}/items")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class OrderItemController {

    final OrderItemService orderItemService;
    final OrderItemDtoMapper orderItemDtoMapper;

    @GetMapping
    public List<OrderItemDto> getOrderItems(@PathVariable Long orderId) {
        return orderItemService.findOrderItemsByOrder(orderId).stream()
                        .map(i->orderItemDtoMapper.convert(i))
                                .collect(Collectors.toList());
    }

}
