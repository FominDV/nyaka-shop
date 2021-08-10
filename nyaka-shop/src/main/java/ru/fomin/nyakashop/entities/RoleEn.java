package ru.fomin.nyakashop.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleEn extends BaseTimeEn {

    @Column(name = "role_name")
    String roleName;

}
