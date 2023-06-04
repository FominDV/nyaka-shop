package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shipment extends BaseEntity{

    @Column(name = "created_at")
    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    LocalDateTime createdAt;

    @Column(name = "quantity")
    Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

}
