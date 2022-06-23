package com.anymv.dto.mangaupdates;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MangaUpdatesRank {
    private MangaUpdatesPosition position;
    @JsonProperty("old_position")
    private MangaUpdatesPosition oldPosition;

    public MangaUpdatesPosition getPosition() {
        return position;
    }

    public void setPosition(MangaUpdatesPosition position) {
        this.position = position;
    }

    public MangaUpdatesPosition getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(MangaUpdatesPosition oldPosition) {
        this.oldPosition = oldPosition;
    }
}
