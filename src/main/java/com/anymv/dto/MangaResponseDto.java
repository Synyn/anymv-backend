package com.anymv.dto;

import java.util.List;

public class MangaResponseDto {
    private int page;
    private int itemsPerPage;

    private List<MangaDto> records;
    private int totalResults;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public List<MangaDto> getRecords() {
        return records;
    }

    public void setRecords(List<MangaDto> records) {
        this.records = records;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
