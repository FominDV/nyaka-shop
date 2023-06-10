package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.PasswordDto;
import ru.fomin.nyakashop.dto.UserDto;
import ru.fomin.nyakashop.entities.Role;
import ru.fomin.nyakashop.entities.User;
import ru.fomin.nyakashop.repositories.RoleRepository;
import ru.fomin.nyakashop.repositories.UserRepository;
import ru.fomin.nyakashop.util.SecurityUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Transactional
public class UserController {

    final UserRepository userRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final RoleRepository roleRepository;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");

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

    @GetMapping
    public UserDto getUser(){
       var user = userRepository.findByLogin(SecurityUtils.getEmail()).get();
       return UserDto.builder()
               .login(user.getLogin())
               .email(user.getEmail())
               .firstName(user.getFirstName())
               .lastName(user.getLastName())
               .fatherName(user.getFatherName())
               .createdAt(dtf.format(user.getCreatedAt()))
               .build();
    }

    @PutMapping
    public HttpStatus updateUser(@RequestBody UserDto userDto){
        var users = userRepository.findAll();
        var user = userRepository.findByLogin(userDto.getLogin()).get();
        if(users.stream().anyMatch(u->u.getEmail().equals(userDto.getEmail())) && !user.getEmail().equals(userDto.getEmail())){
            return HttpStatus.BAD_GATEWAY;
        }
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setFatherName(userDto.getFatherName());
        userRepository.save(user);
        return HttpStatus.OK;
    }

    @PutMapping("/password")
    public HttpStatus updatePassword(@RequestBody PasswordDto passwordDto){
        var user = userRepository.findByLogin(SecurityUtils.getEmail()).get();
        if(!bCryptPasswordEncoder.matches(passwordDto.getCurrentPassword(),user.getPassword())){
            return HttpStatus.BAD_GATEWAY;
        }
        user.setPassword(bCryptPasswordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
        return HttpStatus.OK;
    }

}
