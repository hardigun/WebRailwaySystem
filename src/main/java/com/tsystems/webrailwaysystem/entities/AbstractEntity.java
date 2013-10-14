package com.tsystems.webrailwaysystem.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Contract: common entity for other entities and AbstractDAO
 *
 * Date: 20.09.13
 */
@Entity
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
