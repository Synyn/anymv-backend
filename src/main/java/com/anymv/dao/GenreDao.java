package com.anymv.dao;

import com.anymv.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreDao extends JpaRepository<Genre, Long> {

    @Query("SELECT genre from Genre genre WHERE genre.genre IN (?1)")
    List<Genre> findAllGenresByGenreName(List<String> genres);
}
