package ru.fomin.nyakashop.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEn extends BaseTimeEn {

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEn user;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<OrderItemEn> items;

    BigDecimal totalPrice;

    public OrderEn(UserEn user, List<OrderItemEn> items, BigDecimal totalPrice) {
        this.user = user;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public OrderEn(UserEn user) {
        this.user = user;
        this.totalPrice = BigDecimal.ZERO;
    }

}
