package ru.fomin.nyakashop.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "base_time_entity")
@Inheritance(strategy = InheritanceType.JOINED)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@MappedSuperclass
public abstract class BaseTimeEn extends AbstractPersistable<Long>{

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

}
