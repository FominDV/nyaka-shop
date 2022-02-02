package ru.fomin.nyakashop.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.ProductDto;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.mappers.UniversalMapper;
import ru.fomin.nyakashop.services.ProductService;
import ru.fomin.nyakashop.services.impl.ResourceServiceImpl;
import ru.fomin.nyakashop.util.specifications.ProductSpecificationBuilder;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {

    final ProductService productService;
    final UniversalMapper universalMapper;
    final ProductSpecificationBuilder productSpecificationBuilder;

    final ResourceServiceImpl imageService;

    @GetMapping(value = "/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return universalMapper.convert(productService.getProductOrThrow(id), ProductDto.class);
    }

    @ApiOperation(value = "Create a new 'Student' object")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Number of the page", required = true, paramType = "query"),
            @ApiImplicitParam(name = "id",defaultValue = "12",value = "Enter an ID for the student.", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "Enter a Name for the Student.", dataTypeClass = String.class, paramType = "query")
    })
    @GetMapping
    public Page<ProductDto> getProductList(
            @RequestParam(name = "page", defaultValue = "1") int pageIndex,
          @ApiIgnore @RequestParam MultiValueMap<String, String> filterMap
    ) {
        Specification<Product> specification = productSpecificationBuilder.build(filterMap);
        Page<Product> productPage = productService.getProductsByFilter(--pageIndex, specification);
        return productPage.map(product -> universalMapper.convert(product, ProductDto.class));
    }

    @PostMapping
    @ResponseBody
    public Long createNewProduct(@RequestBody ProductDto newProductDtoDto) {
        return productService.create(
                newProductDtoDto.getTitle(),
                newProductDtoDto.getDescription(),
                newProductDtoDto.getCategory(),
                newProductDtoDto.getPrice()
        ).getId();
    }

    @PutMapping
    public void updateProduct(@RequestBody ProductDto productDto) {
        productService.update(universalMapper.convert(productDto, Product.class));
    }

}
