package com.anymv.controller;

import com.anymv.dto.MangaDto;
import com.anymv.entity.Manga;
import com.anymv.entity.Type;
import com.anymv.service.MangaService;
import com.anymv.util.AnyMvPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public Manga getOneById(@PathVariable("id") Long id) {
        return mangaService.findOneById(id);
    }

    @GetMapping("/newest")
    public List<Manga> getNewest(@RequestParam(name = "type") Type type,
                                 @RequestParam(name = "limit") Integer limit) {
        return mangaService.getNewest(type, limit);
    }

}
