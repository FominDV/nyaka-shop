package ru.fomin.nyakashop.services;

import ru.fomin.nyakashop.entities.PriceEn;
import ru.fomin.nyakashop.entities.ProductEn;

import java.math.BigDecimal;

public interface PriceService {

    PriceEn create(BigDecimal cost, ProductEn productEn);

}
