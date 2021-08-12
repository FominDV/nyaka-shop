package ru.fomin.nyakashop.mappers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.dto.OrderDto;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.entities.Order;

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

    public List<OrderDto> convertToOrderList(List<Order> orderEnList) {
        return orderEnList.stream()
                .map(this::convertToOrder)
                .collect(Collectors.toList());
    }

    public OrderDto convertToOrder(Order orderEn) {
        List<OrderItemDto> orderItemDtoList = orderItemMapper.convertToOrderItemList(orderEn.getItems());
        return OrderDto.builder()
                .id(orderEn.getId())
                .orderItemDtoList(orderItemDtoList)
                .totalPrice(orderEn.getTotalPrice())
                .totalQuantity(getTotalQuantity(orderItemDtoList))
                .createAt(dataMapper.convertToString(orderEn.getCreatedAt()))
                .build();
    }

    private int getTotalQuantity(List<OrderItemDto> orderItemDtoList) {
        return orderItemDtoList.stream()
                .mapToInt(orderItem -> orderItem.getQuantity())
                .sum();
    }

}
