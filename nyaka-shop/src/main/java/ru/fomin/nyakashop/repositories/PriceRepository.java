package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fomin.nyakashop.entities.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
