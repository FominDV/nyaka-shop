package ru.fomin.nyakashop.mappers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.entities.OrderItem;
@Service
public class OrderItemDtoMapper {

    @Autowired
    ProductMapperCast productMapperCast;

   public OrderItemDto convert(OrderItem orderItem){
        return OrderItemDto.builder()
                .product(productMapperCast.convert(orderItem.getProduct()))
                .priceId(orderItem.getProduct().getPrice().getId())
                .quantity(orderItem.getQuantity())
                .build();
    }

}
