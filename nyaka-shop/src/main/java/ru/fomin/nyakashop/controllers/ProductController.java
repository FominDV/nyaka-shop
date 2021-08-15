package ru.fomin.nyakashop.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.mappers.MapperDto;
import ru.fomin.nyakashop.services.ProductService;
import ru.fomin.nyakashop.util.specifications.ProductSpecificationBuilder;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {

    final ProductService productService;
    final ProductSpecificationBuilder productSpecificationBuilder;

    @GetMapping(value = "/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return MapperDto.INSTANCE.toProductDto(productService.getProductOrThrow(id));
    }

    @GetMapping
    public Page<ProductDto> getProductList(
            @RequestParam(name = "page", defaultValue = "1") int pageIndex,
            @RequestParam(name = "min", required = false) BigDecimal minPrice,
            @RequestParam(name = "max", required = false) BigDecimal maxPrice,
            @RequestParam(name = "title", required = false) String title
    ) {
        Specification<Product> specification = productSpecificationBuilder.build(minPrice, maxPrice, title);
        Page<Product> productPage = productService.getProductsByFilter(--pageIndex, specification);
        return productPage.map(MapperDto.INSTANCE::toProductDto);
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
