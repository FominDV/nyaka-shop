package ru.fomin.nyakashop.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fomin.nyakashop.entities.Role;
import ru.fomin.nyakashop.entities.User;
import ru.fomin.nyakashop.repositories.UserRepository;
import ru.fomin.nyakashop.services.UserService;
import ru.fomin.nyakashop.util.SecurityUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findCurrentUser() throws UsernameNotFoundException {
        String email = SecurityUtils.getEmail();
        return getUserEnByLogin(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = getUserEnByLogin(login);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private User getUserEnByLogin(String login) throws UsernameNotFoundException {
        return findByUsername(login).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с логином '%s' не найден", login)));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
}