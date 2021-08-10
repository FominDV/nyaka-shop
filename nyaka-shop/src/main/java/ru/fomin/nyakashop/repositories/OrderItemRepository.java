package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fomin.nyakashop.entities.OrderItemEn;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEn, Long> {

}
