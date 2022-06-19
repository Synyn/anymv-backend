package com.anymv.entity;

import javax.persistence.Column;

public class Type extends BaseEntity {
    @Column(columnDefinition = "varchar")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
