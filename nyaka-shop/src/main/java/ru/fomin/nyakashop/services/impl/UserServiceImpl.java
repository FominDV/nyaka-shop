package ru.fomin.nyakashop.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fomin.nyakashop.entities.Role;
import ru.fomin.nyakashop.entities.User;
import ru.fomin.nyakashop.repositories.UserRepo;
import ru.fomin.nyakashop.services.UserService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepo userRepository;

    @Override
    public Optional<User> findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findCurrentUser() throws UsernameNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserEnByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserEnByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private User getUserEnByEmail(String email) throws UsernameNotFoundException{
        return findByUsername(email).orElseThrow(() -> new UsernameNotFoundException(String.format("User with email '%s' was not found", email)));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
}