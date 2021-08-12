package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "order_items")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemEn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "price_id", nullable = false)
    Price price;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    public OrderItemEn(Product product) {
        price = product.getPrice();
        quantity = 1;
    }

    public OrderItemEn(Product product, int quantity) {
        this.product=product;
        price = product.getPrice();
        this.quantity = quantity;
    }

}
