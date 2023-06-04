package ru.fomin.nyakashop.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends BaseEntity{

    @Column(name = "role_name", nullable = false)
    String roleName;

}
