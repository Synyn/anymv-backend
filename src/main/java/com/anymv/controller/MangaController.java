package com.anymv.controller;

import com.anymv.entity.Manga;
import com.anymv.service.MangaService;
import com.anymv.util.AnyMvPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This Controller handles all the manga related requests.
 */
@RestController
@RequestMapping("/manga")
public class MangaController {

    @Autowired
    private MangaService mangaService;
    @GetMapping
    public AnyMvPage<Manga> search(@RequestParam(required = true) MultiValueMap<String, Object> query) {
        return mangaService.search(query);
    }

}
