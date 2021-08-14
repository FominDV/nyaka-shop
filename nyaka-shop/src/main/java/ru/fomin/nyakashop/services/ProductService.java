package ru.fomin.nyakashop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;

public interface ProductService {

    Page<Product> getProductsByFilter(int pageIndex, Specification<Product> specification);

    ProductDto getProduct(Long productId);

    void updateProduct(ProductDto productDto);

}
