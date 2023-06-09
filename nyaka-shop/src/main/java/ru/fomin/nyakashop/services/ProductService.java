package ru.fomin.nyakashop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.fomin.nyakashop.dto.ProductsPage;
import ru.fomin.nyakashop.entities.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    ProductsPage getProductsByFilter(int pageIndex, MultiValueMap<String, String> filterMap);

    Optional<Product> getProduct(Long id);

    Product getProductOrThrow(Long id);

    Product update(Product product, BigDecimal price);

    Product create(Product product);

    Product create(String title, String description, List<Long> categories, BigDecimal price, Long brandID, Long countryID, String barcode);

    void setImage(Long productId, UUID imageId);

}
