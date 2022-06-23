package com.anymv.dto.mangaupdates;

public class MangaUpdatesImage {
    private MangaUpdatesImageUrl url;
    private Integer height;
    private Integer width;

    public MangaUpdatesImageUrl getUrl() {
        return url;
    }

    public void setUrl(MangaUpdatesImageUrl url) {
        this.url = url;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
