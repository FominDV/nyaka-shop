package ru.fomin.nyakashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.fomin.nyakashop.dto.BrandDto;
import ru.fomin.nyakashop.dto.CountryDto;
import ru.fomin.nyakashop.entities.Brand;
import ru.fomin.nyakashop.entities.Country;
import ru.fomin.nyakashop.repositories.CountryRepository;
import ru.fomin.nyakashop.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
@Transactional
public class CountryController {

    final CountryRepository countryRepository;
    final ProductRepository productRepository;

    @GetMapping
    public List<CountryDto> getCategories() {
        return countryRepository.findAll().stream()
                .map(b->CountryDto.builder().id(b.getId()).title(b.getTitle()).build())
                .collect(Collectors.toList());
    }

    @PostMapping
    public HttpStatus createCountry(@RequestBody String countryName){
        var countries = countryRepository.findAll();
        if(countries.stream().map(c->c.getTitle()).anyMatch(t->t.equals(countryName))){
            return HttpStatus.BAD_REQUEST;
        }
        var country = Country.builder()
                .title(countryName)
                .build();
        countryRepository.save(country);
        return HttpStatus.OK;
    }

    @DeleteMapping
    public HttpStatus deleteBrand(@RequestParam(name = "countryId") Long countryId){
        var products = productRepository.findAll();
        if(products.stream().map(p->p.getCountry().getId()).anyMatch(id->id.equals(countryId))){
            return HttpStatus.BAD_REQUEST;
        }
        countryRepository.delete(countryRepository.getById(countryId));
        return HttpStatus.OK;
    }

}
