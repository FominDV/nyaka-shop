package ru.fomin.nyakashop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fomin.nyakashop.entities.ProductEn;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<ProductEn, Long> {

    @Query("select p from ProductEn p where p.price.cost >= ?1 and p.price.cost <= ?2")
    Page<ProductEn> findAllByMinAndMaxPrice(BigDecimal min, BigDecimal max, Pageable pageable);

}
