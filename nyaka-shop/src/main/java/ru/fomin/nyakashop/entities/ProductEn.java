package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEn extends BaseTimeEn {

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "description")
    String description;

    @OneToOne
    @JoinColumn(name = "price_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    PriceEn price;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<PriceEn> prices;

    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEn category;

    public BigDecimal getCurrentPrice() {
        return price.getCost();
    }

}
