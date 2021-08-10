package ru.fomin.nyakashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fomin.nyakashop.entity.UserEn;

import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<UserEn, UUID> {
}
