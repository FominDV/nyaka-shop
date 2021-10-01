package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.entities.Price;
import ru.fomin.nyakashop.entities.Product;

import java.math.BigDecimal;

public interface PriceService {

    Price create(BigDecimal cost, Product product);

    Price create(BigDecimal cost);

}
