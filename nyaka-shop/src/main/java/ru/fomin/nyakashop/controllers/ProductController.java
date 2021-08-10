package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.Product;
import ru.fomin.nyakashop.dto.ProductFilter;
import ru.fomin.nyakashop.dto.ProductPage;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.services.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/{id}")
    public Product findById(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @GetMapping
    public ProductPage findAll(@RequestParam(name = "p", defaultValue = "1") int pageIndex) {
        return productService.getProductsByFilter(new ProductFilter(), pageIndex-1);
    }

    @PostMapping
    public Product createNewProduct(@RequestBody Product newProductDto) {
//        Product product = new Product();
//        product.setPrice(newProductDto.getPrice());
//        product.setTitle(newProductDto.getTitle());
//        return new ProductDto(productService.save(product));
        return newProductDto;
    }


}
