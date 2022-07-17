package com.anymv.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "anime")
public class Anime extends BaseEntity {
    @Column(name = "title", columnDefinition = "VARCHAR(255)")
    private String title;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private AnimeType type;

    @Column(name = "episodes", columnDefinition = "SMALLINT")
    private Integer episodes;

    @Enumerated(EnumType.STRING)
    private AnimeStatus status;

    @Enumerated(EnumType.STRING)
    private AnimeSeason season;

    @Column(name = "year", columnDefinition = "SMALLINT")
    private Integer year;

    @Column(name = "imageUrl", columnDefinition = "VARCHAR(255)")
    private String imageUrl;

    @Column(name = "thumbnailUrl", columnDefinition = "VARCHAR(255)")
    private String thumbnailUrl;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "anime_id")
    )
    private List<Genre> genres;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AnimeType getType() {
        return type;
    }

    public void setType(AnimeType type) {
        this.type = type;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public AnimeStatus getStatus() {
        return status;
    }

    public void setStatus(AnimeStatus status) {
        this.status = status;
    }

    public AnimeSeason getSeason() {
        return season;
    }

    public void setSeason(AnimeSeason season) {
        this.season = season;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
