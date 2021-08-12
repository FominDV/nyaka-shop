package ru.fomin.nyakashop.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.fomin.nyakashop.entities.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String email);

    User findCurrentUser();

    UserDetails loadUserByUsername(String email);

}
