package com.anymv.dto.mangaupdates;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MangaUpdatesRecord {
    @JsonProperty("series_id")
    private Long seriesId;

    private String title;

    private String url;

    private String description;

    private MangaUpdatesImage image;

    private String type;

    private String year;

    @JsonProperty("bayesian_rating")
    private Integer bayesianRating;

    @JsonProperty("rating_votes")
    private Integer ratingVotes;

    private List<MangaUpdatesGenre> genres;

    private MangaUpdatesRank rank;

    @JsonProperty("last_updated")
    private MangaUpdatesLastUpdated lastUpdated;

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MangaUpdatesImage getImage() {
        return image;
    }

    public void setImage(MangaUpdatesImage image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getBayesianRating() {
        return bayesianRating;
    }

    public void setBayesianRating(Integer bayesianRating) {
        this.bayesianRating = bayesianRating;
    }

    public Integer getRatingVotes() {
        return ratingVotes;
    }

    public void setRatingVotes(Integer ratingVotes) {
        this.ratingVotes = ratingVotes;
    }

    public List<MangaUpdatesGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<MangaUpdatesGenre> genres) {
        this.genres = genres;
    }

    public MangaUpdatesRank getRank() {
        return rank;
    }

    public void setRank(MangaUpdatesRank rank) {
        this.rank = rank;
    }

    public MangaUpdatesLastUpdated getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(MangaUpdatesLastUpdated lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
