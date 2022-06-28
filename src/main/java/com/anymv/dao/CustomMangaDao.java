package com.anymv.dao;

import com.anymv.dto.MangaSearchDTO;
import com.anymv.entity.Manga;
import com.anymv.entity.Type;
import com.anymv.util.AnyMvPage;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

public interface CustomMangaDao {
    TypedQuery<Manga> findByFilter(MangaSearchDTO filter);
    List<Manga> findNewest(Type type, int limit);
}
