package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.CategoryDto;
import ru.fomin.nyakashop.entities.Category;
import ru.fomin.nyakashop.entities.Country;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.mappers.UniversalMapper;
import ru.fomin.nyakashop.repositories.CategoryRepository;
import ru.fomin.nyakashop.repositories.ProductRepository;
import ru.fomin.nyakashop.services.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Transactional
public class CategoryController {

    final CategoryService categoryService;
    final UniversalMapper universalMapper;
    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryDto> getCategories() {
        return universalMapper.convertList(categoryService.getCategories(), CategoryDto.class);
    }

    @PostMapping
    public HttpStatus createCategory(@RequestBody String categoryName){
        var categories = categoryRepository.findAll();
        if(categories.stream().map(c->c.getTitle()).anyMatch(t->t.equals(categoryName))){
            return HttpStatus.BAD_REQUEST;
        }
        var category = new Category(categoryName);
        categoryRepository.save(category);
        return HttpStatus.OK;
    }

    @DeleteMapping
    public HttpStatus deleteBrand(@RequestParam(name = "categoryId") Long categoryId){
        var products = productRepository.findAll();
        if(products.stream().flatMap(p->p.getCategories().stream()).map(Category::getId).anyMatch(id->id.equals(categoryId))){
            return HttpStatus.BAD_REQUEST;
        }
        categoryRepository.delete(categoryRepository.getById(categoryId));
        return HttpStatus.OK;
    }

}
