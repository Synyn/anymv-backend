package com.anymv.dao.impl;

import com.anymv.dao.AnimeSearchDao;
import com.anymv.entity.Anime;
import com.anymv.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class AnimeSearchDaoImpl implements AnimeSearchDao {

    private Logger logger = LoggerFactory.getLogger(AnimeSearchDaoImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public Page<Anime> search(String name, int page, int size) {
        String query = createSearchQuery();

        int offset = Utils.findOffset(page, size);

        TypedQuery<Anime> animeTypedQuery = entityManager.createQuery(query, Anime.class);
        animeTypedQuery.setParameter(1, "%" + name + "%");

        animeTypedQuery.setFirstResult(offset);
        animeTypedQuery.setMaxResults(size);

        List<Anime> resultList = animeTypedQuery.getResultList();
        Pageable pageable = Pageable.ofSize(size);

        Long maxResults = countAnimeByName(name);

        return new PageImpl<>(resultList, pageable, maxResults);

    }

    @Transactional
    private Long countAnimeByName(String name) {
        String queryStr = "SELECT COUNT(DISTINCT a) FROM Anime a" +
                " JOIN a.synonyms s" +
                " WHERE a.title LIKE ?1 OR s.name LIKE ?1";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter(1, "%" + name + "%");

        return (Long) query.getSingleResult();

    }

    private String createSearchQuery() {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT DISTINCT anime FROM Anime anime" +
                " JOIN anime.synonyms s" +
                " WHERE anime.title LIKE ?1 OR s.name LIKE ?1");

        return sb.toString();
    }
}
