package com.anymv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Entity
@Table(name = "image")
public class Image extends BaseEntity {
    @Column(columnDefinition = "varchar(255)")
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
