package ru.fomin.nyakashop.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(value = "Product category")
public class CategoryDto {

    Long id;
    String title;
    String description;

}
