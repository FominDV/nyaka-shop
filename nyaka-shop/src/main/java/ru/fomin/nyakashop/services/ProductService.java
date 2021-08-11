package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.dto.Product;
import ru.fomin.nyakashop.dto.ProductPage;

import java.math.BigDecimal;

public interface ProductService {

    ProductPage getProductsByFilter(int pageIndex, BigDecimal minPrice, BigDecimal maxPrice);

    Product getProduct(Long productId);

    void updateProduct(Product product);

}
