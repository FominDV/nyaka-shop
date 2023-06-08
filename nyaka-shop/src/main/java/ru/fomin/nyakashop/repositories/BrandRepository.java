package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fomin.nyakashop.entities.Brand;
import ru.fomin.nyakashop.entities.Category;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
