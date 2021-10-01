package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.entities.Category;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.repositories.CategoryRepository;
import ru.fomin.nyakashop.services.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryServiceImpl implements CategoryService {

    final CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryByTitle(String title) {
        return categoryRepository.findFirstByTitle(title);
    }

    @Override
    public Category getCategoryByTitleOrThrow(String title) {
        return getCategoryByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class));
    }

}
