package com.anymv.util;

import com.anymv.entity.Manga;

import javax.persistence.TypedQuery;
import java.util.List;

public class AnyMvPage<T> {
    private int maxPages;
    private int currentPage;

    private List<T> results;
    public AnyMvPage(List<T> resultList, int maxResults, int size, int currentPage) {
        this.results = resultList;

        if(maxResults == 0) {
            maxResults = 1;
        }

        if (size == 0) {
            size = 1;
        }

        this.maxPages = maxResults / size;
        this.currentPage = currentPage;
    }

    public AnyMvPage(TypedQuery<T> resultQuery, int currentPage, int size) {
        int offset = Utils.findOffset(currentPage, size);
        int maxResult = resultQuery.getMaxResults();

        resultQuery.setMaxResults(size);
        resultQuery.setFirstResult(offset);

        this.currentPage = currentPage;
        this.maxPages = size / maxResult;
        this.results = resultQuery.getResultList();
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
