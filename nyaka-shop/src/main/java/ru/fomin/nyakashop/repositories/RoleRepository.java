package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fomin.nyakashop.entities.Role;
import ru.fomin.nyakashop.entities.Shipment;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
