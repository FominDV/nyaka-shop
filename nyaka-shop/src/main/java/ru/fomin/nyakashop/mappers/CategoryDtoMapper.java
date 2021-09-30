package ru.fomin.nyakashop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import ru.fomin.nyakashop.dto.CategoryDto;
import ru.fomin.nyakashop.entities.Category;

@Mapper(componentModel = "spring")
public interface CategoryDtoMapper extends Converter<Category, CategoryDto> {


    @Override
    CategoryDto convert(Category category);
}
