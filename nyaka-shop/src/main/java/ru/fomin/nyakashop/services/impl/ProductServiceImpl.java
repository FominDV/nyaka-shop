package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.dto.Product;
import ru.fomin.nyakashop.dto.ProductFilter;
import ru.fomin.nyakashop.dto.ProductPage;
import ru.fomin.nyakashop.entities.ProductEn;
import ru.fomin.nyakashop.mappers.PageMapper;
import ru.fomin.nyakashop.mappers.ProductMapper;
import ru.fomin.nyakashop.repositories.ProductRepository;
import ru.fomin.nyakashop.services.PriceService;
import ru.fomin.nyakashop.services.ProductService;


import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {

    @Value("${pageSize}")
    int pageSize;

    @Resource
    ProductRepository productRepository;

    @Resource
    ProductMapper productMapper;

    @Resource
    PriceService priceService;

    @Resource
    PageMapper pageMapper;

    @Override
    public ProductPage getProductsByFilter(ProductFilter productFilter, int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductEn> productEnPage = productRepository.findAllByMinAndMaxPrice(
                productFilter.getMinPrice(),
                productFilter.getMaxPrice(),
                pageable
        );
        return pageMapper.convertToProductPage(productEnPage);
    }

    @Override
    public Product getProduct(Long productId) {
        ProductEn productEn = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product was not found"));
        return productMapper.convertToProduct(productEn);
    }

    @Override
    public void updateProduct(Product product) {
        ProductEn productEn = productRepository.getById(product.getId());
        BigDecimal newPrice = product.getPrice();
        if (!newPrice.equals(productEn.getPrice().getCost())) {
            productEn.setPrice(priceService.create(newPrice, productEn));
        }
        productEn.setTitle(product.getTitle());
        productEn.setDescription(product.getDescription());
        productRepository.save(productEn);
    }

}
