package ru.fomin.nyakashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fomin.nyakashop.entities.UserEn;


import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEn,Long> {

    Optional<UserEn> findByEmail(String email);

}
