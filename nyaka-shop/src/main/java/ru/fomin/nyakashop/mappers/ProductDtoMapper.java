package ru.fomin.nyakashop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.services.ResourceService;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ProductDtoMapper implements Converter<Product, ProductDto> {

    @Autowired
    ResourceService resourceService;

    @Override
    @Mapping(target = "price", source = "price.cost")
    @Mapping(target = "category", source = "category.title")
    @Mapping(target = "imageUrl", expression = "java(getImageUrl(source))")
    public abstract ProductDto convert(Product source);

    protected String getImageUrl(Product product) {
        UUID imageId = product.getImageId();
        return imageId == null ?
                "" :
                resourceService.getResourceUrl(imageId);
    }

}
