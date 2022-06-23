package com.anymv.service;

import com.anymv.dao.CustomMangaDao;
import com.anymv.dto.MangaSearchDTO;
import com.anymv.entity.Manga;
import com.anymv.util.AnyMvPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Service
public class MangaService {

    @Autowired
    private CustomMangaDao customMangaDao;

    @Transactional
    public AnyMvPage<Manga> search(MultiValueMap<String, Object> query) {
        MangaSearchDTO searchDTO = new MangaSearchDTO(query);
        TypedQuery<Manga> resultQuery = customMangaDao.findByFilter(searchDTO);

        return new AnyMvPage<>(resultQuery, searchDTO.getPage(), searchDTO.getItems());
    }

}
