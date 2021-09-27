package ru.fomin.nyakashop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper extends Converter<ProductDto, Product> {

    @Override
    @Mapping(target = "price.cost", source = "price")
    @Mapping(target = "category.title", source = "category")
    @Mapping(target = "imageId", ignore = true)
    Product convert(ProductDto source);

}
