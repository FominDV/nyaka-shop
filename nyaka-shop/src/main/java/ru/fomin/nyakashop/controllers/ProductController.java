package ru.fomin.nyakashop.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.services.ProductService;
import ru.fomin.nyakashop.util.specifications.ProductSpecificationBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {

    final ProductService productService;
    final ConversionService conversionService;
    final ProductSpecificationBuilder productSpecificationBuilder;

    @GetMapping(value = "/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return conversionService.convert(productService.getProductOrThrow(id), ProductDto.class);
    }

    @GetMapping
    public Page<ProductDto> getProductList(
            @RequestParam(name = "page", defaultValue = "1") int pageIndex,
            @RequestParam MultiValueMap<String, String> filterMap
    ) {
        Specification<Product> specification = productSpecificationBuilder.build(filterMap);
        Page<Product> productPage = productService.getProductsByFilter(--pageIndex, specification);
        return productPage.map(product -> conversionService.convert(product, ProductDto.class));
    }

    @PostMapping
    public ProductDto createNewProduct(@RequestBody ProductDto newProductDtoDto) {
//        Product product = new Product();
//        product.setPrice(newProductDto.getPrice());
//        product.setTitle(newProductDto.getTitle());
//        return new ProductDto(productService.save(product));
        return newProductDtoDto;
    }


}
