package com.anymv.entity;

import javax.persistence.Column;

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
