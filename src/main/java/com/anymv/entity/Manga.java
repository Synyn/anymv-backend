package com.anymv.entity;

import com.anymv.entity.conveter.EnumTypeConverter;
import org.springframework.data.geo.Shape;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "manga")
public class Manga extends BaseEntity {
    @Column(columnDefinition = "varchar(60)", unique = true, nullable = false)
    private String title;

    @Column(columnDefinition = "varchar(255)")
    private String description;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Convert(converter = EnumTypeConverter.class)
    private Type type;

    @Column(columnDefinition = "varchar(4)")
    private String year;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "manga_id")
    )
    private List<Genre> genres;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
