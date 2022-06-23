package com.anymv.dto;

import com.anymv.entity.Genre;
import com.anymv.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

public class MangaSearchDTO {

    private final String NAME_FIELD = "name";
    private final String DESCRIPTION_FIELD = "description";
    private final String GENRES_FIELD = "genres";
    private final String TYPE_FIELD = "type";
    private final String PAGE_FIELD = "page";
    private final String ITEMS_FIELD = "items";
    private String name;
    private String description;

    private List<String> genres;
    private String type;
    private Integer page;
    private Integer items;

    public MangaSearchDTO(MultiValueMap<String, Object> filter) {
        this.page = (Integer) filter.getFirst(PAGE_FIELD);
        this.items = (Integer) filter.getFirst(ITEMS_FIELD);

        if (this.page == null) {
            this.page = Constants.DEFAULT_PAGE_NUMBER;
        }

        if (this.items == null) {
            this.items = Constants.DEFAULT_ITEMS_NUMBER;
        }

        this.name = (String) filter.getFirst(NAME_FIELD);
        this.description = (String) filter.getFirst(DESCRIPTION_FIELD);
        this.type = (String) filter.getFirst(TYPE_FIELD);

        String genresArray = (String) filter.getFirst(GENRES_FIELD);
        if (!StringUtils.isEmpty(genresArray)) {

            ObjectMapper mapper = new ObjectMapper();

            try {
                this.genres = (ArrayList<String>) mapper.readValue(genresArray, ArrayList.class);
            } catch (JsonProcessingException exception) {
                throw new RuntimeException(exception);

            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }
}
