package com.anymv.service;

import com.anymv.dao.GenreDao;
import com.anymv.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreDao genreDao;

    public List<Genre> getAndCreateGenres(List<String> genres) {
        List<Genre> dbGenres = genreDao.findAllGenresByGenreName(genres);
        return findMatchingGenres(dbGenres, genres);

    }

    private List<Genre> findMatchingGenres(List<Genre> dbGenres, List<String> genres) {
        List<Genre> matchingGenres = new ArrayList<>();

        for (String genre : genres) {
            Genre dbGenre = dbGenres.stream().filter(g -> g.getGenre().equals(genre)).findFirst().orElse(null);

            if (dbGenre != null) {
                matchingGenres.add(dbGenre);
            } else {
                Genre newDbGenre = new Genre();
                newDbGenre.setGenre(genre);

                newDbGenre = genreDao.save(newDbGenre);
                dbGenres.add(newDbGenre);

                matchingGenres.add(newDbGenre);
            }
        }

        return matchingGenres;

    }
}
