package com.anymv.dto.mangaupdates;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MangaUpdatesLastUpdated {
    private Long timestamp;
    @JsonProperty("as_rfc3339")
    private String asRfc3339;
    @JsonProperty("as_string")
    private String asString;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAsRfc3339() {
        return asRfc3339;
    }

    public void setAsRfc3339(String asRfc3339) {
        this.asRfc3339 = asRfc3339;
    }

    public String getAsString() {
        return asString;
    }

    public void setAsString(String asString) {
        this.asString = asString;
    }
}
