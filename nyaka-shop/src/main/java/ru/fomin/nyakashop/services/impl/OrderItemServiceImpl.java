package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.entities.Order;
import ru.fomin.nyakashop.entities.OrderItemEn;
import ru.fomin.nyakashop.repositories.OrderItemRepository;
import ru.fomin.nyakashop.services.OrderItemService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemServiceImpl implements OrderItemService {

    final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItemEn> create(List<OrderItemDto> orderItemDtoList, Order orderEn) {
//        return orderItemDtoList.stream()
//                .map(orderItemMapper::convertToOrderItemEn)
//                .peek(orderItemEn -> orderItemEn.setOrder(orderEn))
//                .map(orderItemRepository::save)
//                .collect(Collectors.toList());
        return null;
    }

}
