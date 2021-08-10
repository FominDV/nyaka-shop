package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "order_items")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemEn extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "price_id", nullable = false)
    PriceEn price;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    ProductEn product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    OrderEn order;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    public OrderItemEn(ProductEn product) {
        price = product.getPrice();
        quantity = 1;
    }

    public OrderItemEn(ProductEn product, int quantity) {
        this.product=product;
        price = product.getPrice();
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }
}
