package ru.fomin.nyakashop.mappers;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.fomin.nyakashop.dto.OrderItemDto;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.OrderItem;
import ru.fomin.nyakashop.entities.Product;

@org.mapstruct.Mapper
public interface Mapper {

    Mapper INSTANCE = Mappers.getMapper( Mapper.class );

    @Mapping(target = "price", source = "price.cost")
    @Mapping(target = "category", source = "category.title")
    ProductDto toProductDto(Product product);

    @Mapping(target = "price.id", source = "priceId")
    @Mapping(target = "product.title", ignore = true)
    @Mapping(target = "product.category", ignore = true)
    @Mapping(target = "product.price", ignore = true)
    @Mapping(target = "product.description", ignore = true)
    OrderItem toOrderItem(OrderItemDto orderItemDto);

}
