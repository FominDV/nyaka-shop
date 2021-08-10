package ru.fomin.nyakashop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fomin.nyakashop.entity.UserEn;
import ru.fomin.nyakashop.repository.UserRepo;

@RestController
public class ProductController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/w")
    public void fff(){
        UserEn userEn = UserEn.builder()
                .email("rrr@yandex.ru")
                .firstName("ddd")
                .lastName("ddsdsd")
                .build();
        userRepo.save(userEn);
    }
}
