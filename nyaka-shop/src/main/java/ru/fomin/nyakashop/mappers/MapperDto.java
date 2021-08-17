package ru.fomin.nyakashop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.factory.Mappers;
import ru.fomin.nyakashop.dto.OrderDto;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Order;
import ru.fomin.nyakashop.entities.OrderItem;
import ru.fomin.nyakashop.entities.Product;

@Mapper
public interface MapperDto {

    MapperDto INSTANCE = Mappers.getMapper( MapperDto.class );

    @Mapping(target = "price", source = "price.cost")
    @Mapping(target = "category", source = "category.title")
    ProductDto toProductDto(Product product);

    @Mapping(target = "price.id", source = "priceId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product.title", ignore = true)
    @Mapping(target = "product.category", ignore = true)
    @Mapping(target = "product.price", ignore = true)
    @Mapping(target = "product.description", ignore = true)
    @Mapping(target = "product.createdAt", ignore = true)
    @Mapping(target = "product.updatedAt", ignore = true)
    @Mapping(target = "product.prices", ignore = true)
    OrderItem toOrderItem(OrderItemDto orderItemDto);

    @Mapping(target = "priceId", source = "price.id")
    OrderItemDto toOrderItemDto(OrderItem orderItem);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd.MM.yyyy HH.mm")
    OrderDto toOrderDto(Order order);

}
