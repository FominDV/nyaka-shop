package ru.fomin.nyakashop.mappers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.dto.ProductPageDto;
import ru.fomin.nyakashop.entities.Product;

import javax.annotation.Resource;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageMapper {

    @Resource
    ProductMapper productMapper;

    public ProductPageDto convertToProductPage(Page<Product> productEnPage) {
        return ProductPageDto.builder()
                .productList(productMapper.convertToProductList(productEnPage.toList()))
                .pageCount(productEnPage.getTotalPages())
                .build();
    }

}
