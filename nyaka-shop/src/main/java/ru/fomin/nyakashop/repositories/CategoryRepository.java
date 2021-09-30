package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fomin.nyakashop.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
