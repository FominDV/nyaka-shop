package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.entities.Shipment;
@Repository
public interface ShipmentsRepository extends JpaRepository<Shipment, Long> {
}
