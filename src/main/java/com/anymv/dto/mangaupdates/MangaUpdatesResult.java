package com.anymv.dto.mangaupdates;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MangaUpdatesResult {
    private MangaUpdatesRecord record;

    @JsonProperty("hit_title")
    private String hitTitle;

    public MangaUpdatesRecord getRecord() {
        return record;
    }

    public void setRecord(MangaUpdatesRecord record) {
        this.record = record;
    }

    public String getHitTitle() {
        return hitTitle;
    }

    public void setHitTitle(String hitTitle) {
        this.hitTitle = hitTitle;
    }
}
