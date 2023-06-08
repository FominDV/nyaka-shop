//package ru.fomin.nyakashop.util.specifications.impl;
//
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Component;
//import org.springframework.util.MultiValueMap;
//import org.springframework.util.StringUtils;
//import ru.fomin.nyakashop.entities.Price_;
//import ru.fomin.nyakashop.entities.Product;
//import ru.fomin.nyakashop.entities.Product_;
//import ru.fomin.nyakashop.util.specifications.ProductSpecificationBuilder;
//
//import javax.annotation.PostConstruct;
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Component
//public class ProductSpecificationBuilderImpl implements ProductSpecificationBuilder {
//
//    Map<Class<?>, Function<String, ?>> castMap;
//
//    @PostConstruct
//    private void init() {
//        castMap = new HashMap<>();
//        castMap.put(String.class, string->string);
//        castMap.put(BigDecimal.class, string->BigDecimal.valueOf(Double.parseDouble(string)));
//    }
//
//    @Override
//    public Specification<Product> build(MultiValueMap<String, String> filterMap) {
//        Specification<Product> spec = Specification.where(null);
//        spec = add(filterMap, BigDecimal.class, spec, "min", this::priceGreaterOrEqualsThan);
//        spec = add(filterMap, BigDecimal.class, spec, "max", this::priceLessOrEqualsThan);
//        spec = add(filterMap, String.class, spec, "title", this::titleLike);
//        return spec;
//    }
//
//    private Specification<Product> priceGreaterOrEqualsThan(BigDecimal minPrice) {
//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.price).get(Price_.cost), minPrice);
//    }
//
//    private Specification<Product> priceLessOrEqualsThan(BigDecimal maxPrice) {
//        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("prices")..get(Price_.cost), maxPrice);
//    }
//
//    private Specification<Product> titleLike(String title) {
//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
//    }
//
//    private <T> Specification<Product> add(MultiValueMap<String, String> filterMap, Class<T> targetClass, Specification<Product> specification, String argument, Function<T, Specification<Product>> specificationFunction) {
//        return StringUtils.hasText(filterMap.getFirst(argument)) ?
//                specification.and(specificationFunction.apply(castArgument(filterMap.getFirst(argument), targetClass))) :
//                specification;
//    }
//
//    private <T> T castArgument(String argument, Class<T> targetClass) {
//        return (T) castMap.get(targetClass).apply(argument);
//    }
//
//}
