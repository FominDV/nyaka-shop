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
public class Product extends BaseTime {

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "description")
    String description;

    @OneToOne
    @JoinColumn(name = "price_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Price price;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Price> prices;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    public BigDecimal getCurrentPrice() {
        return price.getCost();
    }

}