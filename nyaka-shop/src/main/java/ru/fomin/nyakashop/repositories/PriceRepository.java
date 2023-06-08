package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fomin.nyakashop.entities.Price;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findAllByProduct_Id(Long productId);

}
