package ru.fomin.nyakashop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.entities.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends Converter<OrderItemDto, OrderItem> {

    @Override
    @Mapping(target = "price.id", source = "priceId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product.title", ignore = true)
    @Mapping(target = "product.category", ignore = true)
    @Mapping(target = "product.price", ignore = true)
    @Mapping(target = "product.description", ignore = true)
    @Mapping(target = "product.prices", ignore = true)
    OrderItem convert(OrderItemDto source);

}
