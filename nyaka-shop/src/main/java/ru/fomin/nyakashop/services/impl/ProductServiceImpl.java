package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;
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
    public Page<Product> getProductsByFilter(int pageIndex, Specification<Product> specification) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return productRepository.findAll(specification, pageable);
    }

    @Override
    public ProductDto getProduct(Long productId) {
        Product productEn = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product was not found"));
        return productMapper.convertToProduct(productEn);
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        Product productEn = productRepository.getById(productDto.getId());
        BigDecimal newPrice = productDto.getPrice();
        if (!newPrice.equals(productEn.getPrice().getCost())) {
            productEn.setPrice(priceService.create(newPrice, productEn));
        }
        productEn.setTitle(productDto.getTitle());
        productEn.setDescription(productDto.getDescription());
        productRepository.save(productEn);
    }

}
