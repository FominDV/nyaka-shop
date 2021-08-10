package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEn extends BaseTimeEn {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEn user;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<OrderItemEn> items;

    @Column(name = "total_price", nullable = false)
    BigDecimal totalPrice;

}
