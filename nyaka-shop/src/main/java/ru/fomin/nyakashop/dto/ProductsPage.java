package ru.fomin.nyakashop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductsPage {

    Integer totalPages;

    List<ProductDto> content;

}
