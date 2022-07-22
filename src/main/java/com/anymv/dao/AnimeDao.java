package com.anymv.dao;

import com.anymv.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimeDao extends JpaRepository<Anime, Long> {
    @Query("SELECT anime FROM Anime anime " +
            " JOIN anime.synonyms s" +
            " WHERE anime.title LIKE %?1% OR s.name LIKE %?1%")
    public List<Anime> searchByName(String name);
}
