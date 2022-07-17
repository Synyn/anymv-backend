package com.anymv.dao;

import com.anymv.entity.AnimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnimeTypeDao extends JpaRepository<AnimeType, Long> {

    @Query("SELECT type FROM AnimeType type WHERE type.type = ?1")
    public AnimeType findByType(String type);
}
