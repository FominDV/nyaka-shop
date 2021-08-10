package ru.fomin.nyakashop.mappers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.dto.ProductPage;
import ru.fomin.nyakashop.entities.ProductEn;

import javax.annotation.Resource;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageMapper {

    @Resource
    ProductMapper productMapper;

    public ProductPage convertToProductPage(Page<ProductEn> productEnPage) {
        return ProductPage.builder()
                .productList(productMapper.convertToProductList(productEnPage.toList()))
                .pageCount(productEnPage.getTotalPages())
                .build();
    }

}
