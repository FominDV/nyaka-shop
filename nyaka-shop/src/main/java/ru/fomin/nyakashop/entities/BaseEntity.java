package ru.fomin.nyakashop.entities;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

}
