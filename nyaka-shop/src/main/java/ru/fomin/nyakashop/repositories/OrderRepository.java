package ru.fomin.nyakashop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fomin.nyakashop.entities.Order;
import ru.fomin.nyakashop.enums.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    Page<Order> findAllByUser_Login(String email, Pageable pageable);

    Page<Order> findAllByStatusIn(List<OrderStatus> statuses, Pageable pageable );

    boolean existsOrderByIdAndUser_Login(Long id, String email);

}
