package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fomin.nyakashop.dto.BrandDto;
import ru.fomin.nyakashop.dto.CategoryDto;
import ru.fomin.nyakashop.repositories.BrandRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brands")
public class BrandController {

    final BrandRepository brandRepository;

    @GetMapping
    public List<BrandDto> getCategories() {
        return brandRepository.findAll().stream()
                .map(b->BrandDto.builder().id(b.getId()).title(b.getTitle()).build())
                .collect(Collectors.toList());
    }

}
