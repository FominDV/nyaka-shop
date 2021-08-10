package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.dto.Product;
import ru.fomin.nyakashop.dto.ProductFilter;
import ru.fomin.nyakashop.dto.ProductPage;

public interface ProductService {

    ProductPage getProductsByFilter(ProductFilter productFilter, int page);

    Product getProduct(Long productId);

    void updateProduct(Product product);

}
