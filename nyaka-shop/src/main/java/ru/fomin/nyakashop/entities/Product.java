package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
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
public class Product extends BaseEntity {

    @Column(name = "created_at")
    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @EqualsAndHashCode.Exclude
    LocalDateTime updatedAt;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "description")
    String description;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Price> prices;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Shipment> shipments;

    @ManyToMany
    @JoinTable(name = "categories_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;

    @ManyToOne
    @JoinColumn(name = "country_id")
    Country country;

    @Column(name = "image_id")
    UUID imageId;

    public BigDecimal getCost() {
        return getPrice().getCost();
    }

    public Price getPrice() {
        return prices.stream().max(Comparator.comparing(Price::getId)).get();
    }

    public Integer getAllShipmentsQuantity() {
        return shipments.stream().reduce(0, (sh1, sh2) -> sh1 + sh2.getQuantity(), Integer::sum);
    }

}
