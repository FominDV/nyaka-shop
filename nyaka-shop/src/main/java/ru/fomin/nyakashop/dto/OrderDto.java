package ru.fomin.nyakashop.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {

    Long id;
    List<OrderItemDto> orderItemDtoList;
    BigDecimal totalPrice;
    int totalQuantity;
    String createAt;

}
