package com.anymv.controller;

import com.anymv.dto.AnimeDto;
import com.anymv.entity.Anime;
import com.anymv.service.AnimeService;
import com.anymv.util.Constants;
import com.anymv.util.ErrorMessages;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("anime")
public class AnimeController {

    private Logger logger = LoggerFactory.getLogger(AnimeController.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private AnimeService animeService;

    @GetMapping("search")
    public Page<Anime> search(@RequestParam("name") String name,
                              @RequestParam("limit") Integer limit,
                              @RequestParam("page") Integer page) {
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException(ErrorMessages.ERROR_SEARCH_NAME_REQUIRED);
        }

        if (limit == null) {
            limit = Constants.DEFAULT_ITEMS_NUMBER;
        }

        if (page == null) {
            page = Constants.DEFAULT_PAGE_NUMBER;
        }


        return animeService.search(name, limit, page);
    }

}
