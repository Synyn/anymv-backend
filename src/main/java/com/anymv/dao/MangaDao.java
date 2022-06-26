package com.anymv.dao;

import com.anymv.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaDao extends JpaRepository<Manga, Long> {
}
