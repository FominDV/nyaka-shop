package ru.fomin.nyakashop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fomin.nyakashop.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    Page<Order> findAllByUser_Login(String email, Pageable pageable);

    boolean existsOrderByIdAndUser_Login(Long id, String email);

}
