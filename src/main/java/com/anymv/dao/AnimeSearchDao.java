package com.anymv.dao;

import com.anymv.entity.Anime;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AnimeSearchDao {
    public Page<Anime> search(String name, int page, int size);
}
