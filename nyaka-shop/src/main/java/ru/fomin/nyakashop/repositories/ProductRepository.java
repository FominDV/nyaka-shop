package ru.fomin.nyakashop.repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fomin.nyakashop.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



}
