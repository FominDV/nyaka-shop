package ru.fomin.nyakashop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemDto {

    ProductDto product;

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
