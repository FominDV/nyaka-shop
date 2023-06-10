package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fomin.nyakashop.dto.UserDto;
import ru.fomin.nyakashop.entities.Role;
import ru.fomin.nyakashop.entities.User;
import ru.fomin.nyakashop.repositories.RoleRepository;
import ru.fomin.nyakashop.repositories.UserRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Transactional
public class UserController {

    final UserRepository userRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final RoleRepository roleRepository;

    @PostMapping
    public HttpStatus createUser(@RequestBody UserDto userDto){
        var users = userRepository.findAll();
        if(users.stream().anyMatch(u->u.getLogin().equals(userDto.getLogin()))){
            return HttpStatus.BAD_REQUEST;
        }
        if(users.stream().anyMatch(u->u.getEmail().equals(userDto.getEmail()))){
            return HttpStatus.BAD_GATEWAY;
        }
        var user = User.builder()
                .login(userDto.getLogin())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .fatherName(userDto.getFatherName())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .roles(List.of(roleRepository.getById(1L)))
                .build();
        userRepository.save(user);
        return HttpStatus.OK;
    }

}
