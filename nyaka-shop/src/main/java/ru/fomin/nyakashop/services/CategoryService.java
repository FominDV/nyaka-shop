package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategories();

    Optional<Category> getCategoryByTitle(String title);

    Category getCategoryByTitleOrThrow(String title);

}
