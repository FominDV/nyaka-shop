package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

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
