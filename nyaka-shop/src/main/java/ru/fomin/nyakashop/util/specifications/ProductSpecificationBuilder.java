package ru.fomin.nyakashop.util.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.fomin.nyakashop.entities.Product;

import java.math.BigDecimal;

public interface ProductSpecificationBuilder {

    Specification<Product> build(BigDecimal minPrice, BigDecimal maxPrice, String title);

}
