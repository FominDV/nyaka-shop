package ru.fomin.nyakashop.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends BaseEntity {

    @Column(name = "title", nullable = false)
    String title;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<Product> products;

}
