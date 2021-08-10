package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.dto.OrderItem;
import ru.fomin.nyakashop.entities.OrderEn;
import ru.fomin.nyakashop.entities.OrderItemEn;
import java.util.List;

public interface OrderItemService {

    List<OrderItemEn> create(List<OrderItem> orderItemList, OrderEn orderEn);

}
