package ru.fomin.nyakashop.mappers;

import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public List<ProductDto> convertToProductList(List<Product> productEnList) {
        return productEnList.stream()
                .map(this::convertToProduct)
                .collect(Collectors.toList());
    }

    public ProductDto convertToProduct(Product productEn) {
        return ProductDto.builder()
                .id(productEn.getId())
                .title(productEn.getTitle())
                .description(productEn.getDescription() != null ? productEn.getDescription() : "")
                .price(productEn.getCurrentPrice())
                .build();
    }

}
