package com.anymv.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


public enum Type {
    ARTBOOK("Artbook"),
    DOUJINSHI("Doujinshi"),
    DRAMA_CD("Drama CD"),
    FILIPINO("Filipino"),
    INDONESIAN("Indonesian"),
    MANGA("Manga"),
    MANHWA("Manhwa"),
    MANHUA("Manhua"),
    NOVEL("Novel"),
    OEL("OEL"),
    THAI("Thai"),
    VIETNAMESE("Vietnamese"),
    MALAYSIAN("Malaysian"),
    NORDIC("Nordic"),
    FRENCH("French"),
    SPANISH("Spanish");

    private String value;

    Type(String value) {
        this.value = value;
    }

    public static Type getType(String value) {
        Type[] types = Type.values();

        Type foundType = null;

        for(Type type: types) {
            if(StringUtils.equals(type.getValue(), value)) {
                foundType = type;
                break;
            }
        }

        return foundType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
