package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fomin.nyakashop.dto.BrandDto;
import ru.fomin.nyakashop.dto.CountryDto;
import ru.fomin.nyakashop.repositories.CountryRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
public class CountryController {

    final CountryRepository countryRepository;

    @GetMapping
    public List<CountryDto> getCategories() {
        return countryRepository.findAll().stream()
                .map(b->CountryDto.builder().id(b.getId()).title(b.getTitle()).build())
                .collect(Collectors.toList());
    }

}
