package ru.fomin.nyakashop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.entities.OrderItem;

@Mapper(componentModel = "spring", uses = ProductDtoMapper.class)
public interface OrderItemDtoMapper extends Converter<OrderItem, OrderItemDto> {

    @Override
    @Mapping(target = "priceId", expression = "java(getPriceId(orderItem))")
    OrderItemDto convert(OrderItem orderItem);

    default Long getPriceId(OrderItem orderItem){
        return orderItem.getProduct().getPrices().stream().map(p->p.getId()).max(Long::compareTo).get();
    }

}
