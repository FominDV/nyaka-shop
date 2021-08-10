package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.dto.OrderItem;
import ru.fomin.nyakashop.entities.OrderEn;
import ru.fomin.nyakashop.entities.OrderItemEn;
import ru.fomin.nyakashop.mappers.OrderItemMapper;
import ru.fomin.nyakashop.repositories.OrderItemRepository;
import ru.fomin.nyakashop.services.OrderItemService;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    OrderItemRepository orderItemRepository;

    @Resource
    OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemEn> create(List<OrderItem> orderItemList, OrderEn orderEn) {
        return orderItemList.stream()
                .map(orderItemMapper::convertToOrderItemEn)
                .peek(orderItemEn -> orderItemEn.setOrder(orderEn))
                .map(orderItemRepository::save)
                .collect(Collectors.toList());
    }

}
