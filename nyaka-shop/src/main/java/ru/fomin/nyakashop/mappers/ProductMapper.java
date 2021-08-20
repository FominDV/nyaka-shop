package ru.fomin.nyakashop.mappers;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper extends Converter<Product, ProductDto> {

    @Override
    @Mapping(target = "price", source = "price.cost")
    @Mapping(target = "category", source = "category.title")
    ProductDto convert(Product source);

}
