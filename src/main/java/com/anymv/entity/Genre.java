package com.anymv.entity;

import javax.persistence.Column;

public class Genre extends BaseEntity {
    @Column(columnDefinition = "varchar(255)")
    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
