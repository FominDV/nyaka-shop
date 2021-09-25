package ru.fomin.nyakashop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.fomin.nyakashop.entities.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    Page<Product> getProductsByFilter(int pageIndex, Specification<Product> specification);

    Optional<Product> getProduct(Long id);

    Product getProductOrThrow(Long id);

    Product update(Product product);

    void setImage(Long productId, UUID imageId);

}
