package ru.fomin.nyakashop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductPageDto {
    List<ProductDto> productList;
    int pageCount;
}
