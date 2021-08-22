package ru.fomin.nyakashop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.entities.OrderItem;

@Mapper(componentModel = "spring", uses = ProductDtoMapper.class)
public interface OrderItemDtoMapper extends Converter<OrderItem, OrderItemDto> {

    @Override
    @Mapping(target = "priceId", source = "price.id")
    OrderItemDto convert(OrderItem orderItem);

}
