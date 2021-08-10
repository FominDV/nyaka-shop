package ru.fomin.nyakashop.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class CategoryEn extends BaseTimeEn{

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "category")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ProductEn> products;

}
