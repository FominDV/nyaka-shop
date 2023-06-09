package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    @OneToMany(mappedBy = "orderItem")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Feedback> feedbacks;

    @Column(name = "cost")
    BigDecimal cost;

    public Price getPrice(){
        return product.getPrice();
    }

    //public BigDecimal getCost(){
     //   return product.getCost();
  //  }

}
