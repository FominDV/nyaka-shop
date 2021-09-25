package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
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

    @Column(name = "image_id")
    UUID imageId;

    public BigDecimal getCurrentPrice() {
        return price.getCost();
    }

}
