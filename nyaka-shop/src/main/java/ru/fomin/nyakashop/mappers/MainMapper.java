package ru.fomin.nyakashop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;

@Mapper
public interface MainMapper {

    MainMapper INSTANCE = Mappers.getMapper( MainMapper.class );

    @Mapping(target = "price", source = "price.cost")
    ProductDto toProductDto(Product product);

}
