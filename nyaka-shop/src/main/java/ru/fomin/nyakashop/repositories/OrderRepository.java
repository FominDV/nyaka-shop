package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fomin.nyakashop.entities.OrderEn;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEn, Long> {

    List<OrderEn> findAllByUser_EmailOrderByCreatedAtDesc(String email);

}
