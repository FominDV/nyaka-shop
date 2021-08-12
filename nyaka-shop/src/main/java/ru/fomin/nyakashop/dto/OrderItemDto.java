package ru.fomin.nyakashop.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemDto {

    @EqualsAndHashCode.Exclude
    ProductDto productDto;

    Long priceId;

    @EqualsAndHashCode.Exclude
    int quantity;

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

}
