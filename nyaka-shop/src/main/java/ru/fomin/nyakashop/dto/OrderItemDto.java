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

    ProductDto productDto;

    Long priceId;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    int quantity = 1;

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public boolean isEmpty() {
        return quantity <= 0;
    }

}
