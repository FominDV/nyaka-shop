package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Feedback extends BaseEntity {

    @Column(name = "created_at")
    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    LocalDateTime createdAt;

    @Column(name = "text")
    String text;

    @ManyToOne
    @JoinColumn(name = "order_item_id")
    OrderItem orderItem;

}
