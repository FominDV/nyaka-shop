package ru.fomin.nyakashop.services;

import org.springframework.data.domain.Page;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;

import java.math.BigDecimal;

public interface ProductService {

    Page<Product> getProductsByFilter(int pageIndex, BigDecimal minPrice, BigDecimal maxPrice);

    ProductDto getProduct(Long productId);

    void updateProduct(ProductDto productDto);

}
