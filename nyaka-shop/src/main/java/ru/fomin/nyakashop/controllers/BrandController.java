package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.BrandDto;
import ru.fomin.nyakashop.dto.CategoryDto;
import ru.fomin.nyakashop.entities.Brand;
import ru.fomin.nyakashop.repositories.BrandRepository;
import ru.fomin.nyakashop.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brands")
@Transactional
public class BrandController {

    final BrandRepository brandRepository;
    final ProductRepository productRepository;

    @GetMapping
    public List<BrandDto> getCategories() {
        return brandRepository.findAll().stream()
                .map(b->BrandDto.builder().id(b.getId()).title(b.getTitle()).build())
                .collect(Collectors.toList());
    }

    @PostMapping
    public HttpStatus createBrand(@RequestBody String brandName){
        var brands = brandRepository.findAll();
        if(brands.stream().map(b->b.getTitle()).anyMatch(t->t.equals(brandName))){
            return HttpStatus.BAD_REQUEST;
        }
        var brand = Brand.builder()
                .title(brandName)
                .build();
        brandRepository.save(brand);
        return HttpStatus.OK;
    }

    @DeleteMapping
    public HttpStatus deleteBrand(@RequestParam(name = "brandId") Long brandId){
        var products = productRepository.findAll();
        if(products.stream().map(p->p.getBrand().getId()).anyMatch(id->id.equals(brandId))){
            return HttpStatus.BAD_REQUEST;
        }
        brandRepository.delete(brandRepository.getById(brandId));
        return HttpStatus.OK;
    }

}
