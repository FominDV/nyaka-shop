package ru.fomin.nyakashop.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrandDto {
    Long id;
    String title;
}
