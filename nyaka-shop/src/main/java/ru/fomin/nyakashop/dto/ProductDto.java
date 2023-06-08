package ru.fomin.nyakashop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {

    Long id;
    String title;
    String description;
    List<CategoryDto> categories;
    BrandDto brand;
    CountryDto country;
    BigDecimal price;
    Long priceID;
    String imageUrl;
    Integer quentity;
    Boolean isBoughtByUser;

}
