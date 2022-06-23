package com.anymv.dao.impl;

import com.anymv.dao.CustomMangaDao;
import com.anymv.dto.MangaSearchDTO;
import com.anymv.entity.Manga;
import com.anymv.util.AnyMvPage;
import com.anymv.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerMangaDaoImpl implements CustomMangaDao {

    @Autowired
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(CustomerMangaDaoImpl.class);

    @Override
    @Transactional
    public TypedQuery<Manga> findByFilter(MangaSearchDTO filter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT manga FROM Manga manga");

        addJoins(stringBuilder);
        addFilters(filter, stringBuilder);
        logger.info("Full Query -> " + stringBuilder.toString());

        TypedQuery<Manga> query = entityManager.createQuery(stringBuilder.toString(), Manga.class);
//
//        int offset = Utils.findOffset(filter.getPage(), filter.getItems());
//        int maxResults = query.getMaxResults();
//
//
//        query.setFirstResult(offset);
//        query.setMaxResults(filter.getItems());

        return query;
    }

    private void addFilters(MangaSearchDTO filter, StringBuilder stringBuilder) {
        stringBuilder.append(" WHERE");

        List<String> whereList = new ArrayList<>();

        if (!StringUtils.isEmpty(filter.getDescription())) {
            whereList.add(" manga.description LIKE %" + filter.getDescription() + "%");
        }

        if (!StringUtils.isEmpty(filter.getName())) {
            whereList.add(" manga.name LIKE %" + filter.getName() + "%");
        }

        if (!StringUtils.isEmpty(filter.getType())) {
            whereList.add(" type.name LIKE %" + filter.getType() + "%");
        }

        if (filter.getGenres() != null && !filter.getGenres().isEmpty()) {
            StringBuilder genresBuilder = new StringBuilder();
            List<String> genres = filter.getGenres();

            for (int i = 0; i < genres.size(); i++) {
                String genre = genres.get(i);

                if (i > 0) {
                    genresBuilder.append(" OR");
                }

                genresBuilder.append(" genres.name LIKE %").append(genre).append("%");
            }

            whereList.add(genresBuilder.toString());
        }

        for (int i = 0; i < whereList.size(); i++) {
            String where = whereList.get(i);

            if(i > 0) {
                stringBuilder.append("AND");
            }

            stringBuilder.append(where);
        }
        logger.info(stringBuilder.toString());
    }

    private void addJoins(StringBuilder stringBuilder) {
        stringBuilder.append(" JOIN FETCH manga.image image");
        stringBuilder.append(" JOIN FETCH manga.type type");
        stringBuilder.append(" JOIN FETCH manga.genres genres");
    }
}
