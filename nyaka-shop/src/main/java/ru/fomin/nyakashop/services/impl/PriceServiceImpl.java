package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.entities.Price;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.exceptions.ResourceNotFoundException;
import ru.fomin.nyakashop.repositories.PriceRepository;
import ru.fomin.nyakashop.services.PriceService;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceServiceImpl implements PriceService {

    @Resource
    PriceRepository priceRepository;

    @Override
    public Price create(BigDecimal cost, Product productEn) {
        Price priceEn = Price.builder()
                .cost(cost)
                .product(productEn)
                .build();
        return priceRepository.save(priceEn);
    }

    @Override
    public Price getPriceOrThrow(Long priceId) {
        return priceRepository.findById(priceId)
                .orElseThrow(()->new ResourceNotFoundException(Price.class));
    }

}
