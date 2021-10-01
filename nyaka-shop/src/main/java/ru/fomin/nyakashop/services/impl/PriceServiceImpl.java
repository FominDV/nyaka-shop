package ru.fomin.nyakashop.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.fomin.nyakashop.entities.Price;
import ru.fomin.nyakashop.entities.Product;
import ru.fomin.nyakashop.repositories.PriceRepository;
import ru.fomin.nyakashop.services.PriceService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceServiceImpl implements PriceService {

    final PriceRepository priceRepository;

    @Override
    public Price create(BigDecimal cost, Product product) {
        return priceRepository.save(new Price(cost, product));
    }

    @Override
    public Price create(BigDecimal cost) {
        return create(cost, null);
    }

}
