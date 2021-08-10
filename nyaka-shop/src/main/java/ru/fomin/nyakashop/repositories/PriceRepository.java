package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fomin.nyakashop.entities.PriceEn;

@Repository
public interface PriceRepository extends JpaRepository<PriceEn, Long> {

}
