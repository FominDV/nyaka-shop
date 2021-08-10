package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
//    private final CategoryService categoryService;
//
//    @GetMapping("/{id}")
//    public Category findById(@PathVariable Long id) {
//        Category c = categoryService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found, id: " + id));
//        return c;
//    }
}
