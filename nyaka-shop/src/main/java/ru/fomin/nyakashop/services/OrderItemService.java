package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.entities.Order;
import ru.fomin.nyakashop.entities.OrderItemEn;
import java.util.List;

public interface OrderItemService {

    List<OrderItemEn> create(List<OrderItemDto> orderItemDtoList, Order orderEn);

}
