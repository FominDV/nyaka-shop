package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.fomin.nyakashop.enums.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseTime {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<OrderItem> items;

    @Column(name = "total_price", nullable = false)
    BigDecimal totalPrice;

    @Column(name = "address", nullable = false)
    String address;

    @Column(name = "phone", nullable = false)
    String phone;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    OrderStatus status = OrderStatus.NEW;


}
