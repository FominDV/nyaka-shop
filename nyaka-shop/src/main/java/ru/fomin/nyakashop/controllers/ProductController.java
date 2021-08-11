package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.Product;
import ru.fomin.nyakashop.dto.ProductPage;
import ru.fomin.nyakashop.services.ProductService;

import java.math.BigDecimal;

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
    public ProductPage getProductList(
            @RequestParam(name = "page", defaultValue = "1") int pageIndex,
            @RequestParam(name = "min", required = false) BigDecimal minPrice,
            @RequestParam(name = "max", required = false) BigDecimal maxPrice
    ) {
        if(minPrice==null){
            minPrice=BigDecimal.ZERO;
        }
        if(maxPrice==null){
            maxPrice=BigDecimal.valueOf(Long.MAX_VALUE);
        }
        return productService.getProductsByFilter(pageIndex, minPrice, maxPrice);
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
