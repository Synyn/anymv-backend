package com.anymv.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "synonyms")
public class Synonym extends BaseEntity {
    @Column(name = "name", columnDefinition = "VARCHAR(1024)")
    private String name;

    @ManyToOne
    @JoinColumn(name = "anime_id", nullable = false)
    @JsonIgnore
    private Anime anime;

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
