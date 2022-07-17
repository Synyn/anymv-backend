package com.anymv.dto.animeoffline;

import java.util.List;

public class AnimeOfflineDbData {
    private List<String> sources;
    private String title;
    private String type;
    private Integer episodes;
    private String status;
    private AnimeOfflineDbAnimeSeason animeSeason;
    private String picture;
    private String thumbnail;
    private List<String> synonyms;
    private List<String> relations;
    private List<String> tags;

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AnimeOfflineDbAnimeSeason getAnimeSeason() {
        return animeSeason;
    }

    public void setAnimeSeason(AnimeOfflineDbAnimeSeason animeSeason) {
        this.animeSeason = animeSeason;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getRelations() {
        return relations;
    }

    public void setRelations(List<String> relations) {
        this.relations = relations;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
