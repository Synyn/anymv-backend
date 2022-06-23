package com.anymv.dto.mangaupdates;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MangaUpdatesPosition {
    private Integer week;

    private Integer month;

    @JsonProperty("three_months")
    private Integer threeMonths;

    @JsonProperty("six_months")
    private Integer sixMonths;

    private Integer year;

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getThreeMonths() {
        return threeMonths;
    }

    public void setThreeMonths(Integer threeMonths) {
        this.threeMonths = threeMonths;
    }

    public Integer getSixMonths() {
        return sixMonths;
    }

    public void setSixMonths(Integer sixMonths) {
        this.sixMonths = sixMonths;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
