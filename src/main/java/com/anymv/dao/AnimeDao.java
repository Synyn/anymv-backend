package com.anymv.dao;

import com.anymv.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeDao extends JpaRepository<Anime, Long> {
}
