package ru.fomin.nyakashop.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.fomin.nyakashop.entities.UserEn;

import java.util.Optional;

public interface UserService {

    Optional<UserEn> findByUsername(String email);

    UserEn findCurrentUser();

    UserDetails loadUserByUsername(String email);

}
