package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.entities.Price;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.repositories.ProductRepository;
import ru.fomin.nyakashop.services.CategoryService;
import ru.fomin.nyakashop.services.PriceService;
import ru.fomin.nyakashop.services.ProductService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;
    final PriceService priceService;
    final CategoryService categoryService;

    @Value("${pageSize.product}")
    int pageSize;

    @Override
    public Page<Product> getProductsByFilter(int pageIndex, Specification<Product> specification) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return productRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product getProductOrThrow(Long id) throws ResourceNotFoundException {
        return getProduct(id).orElseThrow(() -> new ResourceNotFoundException(Product.class));
    }

    @Override
    @Transactional
    public Product update(Product product) {
        Product currentProduct = productRepository.getById(product.getId());
        currentProduct.setDescription(product.getDescription());
        if (!currentProduct.getPrice().getCost().equals(product.getPrice().getCost())) {
            Price newPrice = priceService.create(product.getPrice().getCost(), currentProduct);
            currentProduct.setPrice(newPrice);
        }
        return productRepository.save(currentProduct);
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product create(String title, String description, String category, BigDecimal price) {
        Price newPrice = priceService.create(price);
        Product product = Product.builder()
                .title(title)
                .description(description)
                .price(newPrice)
                .category(categoryService.getCategoryByTitleOrThrow(category))
                .build();
        newPrice.setProduct(product);
        return create(product);
    }

    @Override
    public void setImage(Long productId, UUID imageId) {
        Product product = getProductOrThrow(productId);
        product.setImageId(imageId);
        productRepository.save(product);
    }

}
