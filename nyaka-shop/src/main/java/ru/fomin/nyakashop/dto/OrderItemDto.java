package ru.fomin.nyakashop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemDto {

    ProductDto product;

    Long priceId;

    BigDecimal cost;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    int quantity = 1;

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return quantity <= 0;
    }

}
