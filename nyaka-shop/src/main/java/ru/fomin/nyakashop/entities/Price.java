package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Price extends BaseEntity{

    @Column(name = "created_at")
    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    LocalDateTime createdAt;

    @Column(name = "cost")
    BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    public Price(BigDecimal cost, Product product) {
        this.cost = cost;
        this.product = product;
    }
}
