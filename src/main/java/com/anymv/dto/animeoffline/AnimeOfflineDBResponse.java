package com.anymv.dto.animeoffline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimeOfflineDBResponse {
    private String lastUpdate;
    private List<AnimeOfflineDbData> data;


    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<AnimeOfflineDbData> getData() {
        return data;
    }

    public void setData(List<AnimeOfflineDbData> data) {
        this.data = data;
    }
}
