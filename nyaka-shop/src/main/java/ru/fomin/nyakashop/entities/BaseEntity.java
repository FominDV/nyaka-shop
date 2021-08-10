package ru.fomin.nyakashop.entities;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public abstract class BaseEntity extends AbstractPersistable<Long> {

    @Override
    public void setId(Long id){
         super.setId(id);
    }

}
