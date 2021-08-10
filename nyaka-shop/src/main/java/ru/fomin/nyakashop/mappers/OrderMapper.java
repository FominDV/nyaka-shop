package ru.fomin.nyakashop.mappers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.dto.Order;
import ru.fomin.nyakashop.dto.OrderItem;
import ru.fomin.nyakashop.entities.OrderEn;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderMapper {

    @Resource
    OrderItemMapper orderItemMapper;

    @Resource
    DataMapper dataMapper;

    public List<Order> convertToOrderList(List<OrderEn> orderEnList) {
        return orderEnList.stream()
                .map(this::convertToOrder)
                .collect(Collectors.toList());
    }

    public Order convertToOrder(OrderEn orderEn) {
        List<OrderItem> orderItemList = orderItemMapper.convertToOrderItemList(orderEn.getItems());
        return Order.builder()
                .id(orderEn.getId())
                .orderItemList(orderItemList)
                .totalPrice(orderEn.getTotalPrice())
                .totalQuantity(getTotalQuantity(orderItemList))
                .createAt(dataMapper.convertToString(orderEn.getCreatedAt()))
                .build();
    }

    private int getTotalQuantity(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .mapToInt(orderItem -> orderItem.getQuantity())
                .sum();
    }

}
