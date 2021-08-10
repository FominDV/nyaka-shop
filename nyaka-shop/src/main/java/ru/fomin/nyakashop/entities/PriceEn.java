package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "prices")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceEn extends BaseTimeEn {

    BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductEn product;

    public PriceEn(BigDecimal cost) {
        this.cost = cost;
    }
}
