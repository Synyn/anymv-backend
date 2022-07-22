package com.anymv.service;

import com.anymv.dao.AnimeDao;
import com.anymv.dao.AnimeSearchDao;
import com.anymv.dto.AnimeDto;
import com.anymv.entity.Anime;
import com.anymv.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimeService {

    @Autowired
    private AnimeSearchDao animeSearchDao;

    private Logger logger = LoggerFactory.getLogger(AnimeService.class);

    public Page<Anime> search(String name, Integer size, Integer page) {
        logger.info("Name -> " + name);
        Page<Anime> animePage = animeSearchDao.search(name, page, size);

        return animePage;
    }
}
