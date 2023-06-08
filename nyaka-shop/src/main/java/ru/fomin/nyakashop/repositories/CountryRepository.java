package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fomin.nyakashop.entities.Category;
import ru.fomin.nyakashop.entities.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
