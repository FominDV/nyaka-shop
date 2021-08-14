package ru.fomin.nyakashop.util.specifications.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.util.specifications.ProductSpecificationBuilder;

import java.math.BigDecimal;
import java.util.function.Function;

@Component
public class ProductSpecificationBuilderImpl implements ProductSpecificationBuilder {

    @Override
    public Specification<Product> build(BigDecimal minPrice, BigDecimal maxPrice, String title) {
        Specification<Product> spec = Specification.where(null);
        spec = add(spec, minPrice, this::priceGreaterOrEqualsThan);
        spec = add(spec, maxPrice, this::priceLessOrEqualsThan);
        spec = add(spec, title, this::titleLike);
        return spec;
    }

    private Specification<Product> priceGreaterOrEqualsThan(BigDecimal minPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price").get("cost"), minPrice);
    }

    private Specification<Product> priceLessOrEqualsThan(BigDecimal maxPrice) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price").get("cost"), maxPrice));
    }

    private Specification<Product> titleLike(String title) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    private <T> Specification<Product> add(Specification<Product> specification, T argument, Function<T, Specification<Product>> specificationFunction) {
        return argument == null ?
                specification :
                specification.and(specificationFunction.apply(argument));
    }

}
