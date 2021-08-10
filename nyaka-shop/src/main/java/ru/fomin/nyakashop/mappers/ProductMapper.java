package ru.fomin.nyakashop.mappers;

import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.dto.Product;
import ru.fomin.nyakashop.entities.ProductEn;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public List<Product> convertToProductList(List<ProductEn> productEnList) {
        return productEnList.stream()
                .map(this::convertToProduct)
                .collect(Collectors.toList());
    }

    public Product convertToProduct(ProductEn productEn) {
        return Product.builder()
                .id(productEn.getId())
                .title(productEn.getTitle())
                .description(productEn.getDescription() != null ? productEn.getDescription() : "")
                .price(productEn.getCurrentPrice())
                .build();
    }

}
