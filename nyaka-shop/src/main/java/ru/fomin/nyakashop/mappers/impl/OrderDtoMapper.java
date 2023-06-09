package ru.fomin.nyakashop.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.dto.OrderDto;
import ru.fomin.nyakashop.entities.Order;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class OrderDtoMapper {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");
    final ProductMapperCast productMapperCast;

    public OrderDto convert(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .createdAt(dtf.format(order.getCreatedAt()))
                .updateAt(dtf.format(order.getUpdatedAt()))
                .address(order.getAddress())
                .phone(order.getPhone())
                .status(order.getStatus().name())
                .statusName(order.getStatus().getName())
                .build();
    }
}
