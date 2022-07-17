package com.anymv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "animeType")
public class AnimeType extends BaseEntity {

    @Column(name = "type", columnDefinition = "VARCHAR(50)", unique = true)
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
