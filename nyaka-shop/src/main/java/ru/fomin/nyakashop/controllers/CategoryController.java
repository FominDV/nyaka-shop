package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fomin.nyakashop.dto.CategoryDto;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.mappers.UniversalMapper;
import ru.fomin.nyakashop.services.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    final CategoryService categoryService;
    final UniversalMapper universalMapper;

    @GetMapping
    public List<CategoryDto> getCategories() {
        return universalMapper.convertList(categoryService.getCategories(), CategoryDto.class);
    }

}
