package ru.fomin.nyakashop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fomin.nyakashop.entities.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    Page<Order> findAllByUser_Email(String email, Pageable pageable);

    boolean existsOrderByIdAndUser_Email(Long id, String email);

}
