package ru.fomin.nyakashop.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFilter {

    final BigDecimal maximumPriceLimit = BigDecimal.valueOf( 99999.99);

    BigDecimal minPrice = BigDecimal.ZERO;
    BigDecimal maxPrice = maximumPriceLimit;

    public void setMinPrice(BigDecimal minPrice) {
        if (minPrice == null || minPrice.compareTo( BigDecimal.ZERO)<0) {
            minPrice = BigDecimal.ZERO;
        }
        this.minPrice = minPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        if (maxPrice == null || maxPrice.compareTo( maximumPriceLimit)>0) {
            maxPrice = maximumPriceLimit;
        }
        this.maxPrice = maxPrice;
    }

}
