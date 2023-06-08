package ru.fomin.nyakashop.mappers.impl;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.dto.OrderDto;
import ru.fomin.nyakashop.entities.Order;

import java.time.format.DateTimeFormatter;

@Service
public class OrderDtoMapper {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");

    public OrderDto convert(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .createdAt(dtf.format(order.getCreatedAt()))
                .address(order.getAddress())
                .phone(order.getPhone())
                .status(order.getStatus().name())
                .build();
    }
}
