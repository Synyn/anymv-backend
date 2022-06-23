package com.anymv.dto.mangaupdates;

import java.util.List;

public class MangaUpdatesResponse {
    private int totalHits;
    private int page;
    private int perPage;
    private List<MangaUpdatesResult> results;

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public List<MangaUpdatesResult> getResults() {
        return results;
    }

    public void setResults(List<MangaUpdatesResult> results) {
        this.results = results;
    }
}
