package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.entities.PriceEn;
import ru.fomin.nyakashop.entities.ProductEn;
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
    public PriceEn create(BigDecimal cost, ProductEn productEn) {
        PriceEn priceEn = PriceEn.builder()
                .cost(cost)
                .product(productEn)
                .build();
        return priceRepository.save(priceEn);
    }

}
