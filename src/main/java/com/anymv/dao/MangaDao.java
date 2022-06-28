package com.anymv.dao;

import com.anymv.entity.Manga;
import com.anymv.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MangaDao extends JpaRepository<Manga, Long> {
    @Query("SELECT manga FROM Manga manga WHERE manga.id = ?1")
    public Manga findOneById(Long id);

    @Query("SELECT manga FROM Manga manga WHERE manga.type = ?1 ORDER BY manga.updated DESC LIMIT ?2")
    public List<Manga> findNewest(Type type, int limit);


}
