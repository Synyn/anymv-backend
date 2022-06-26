package com.anymv.service;

import com.anymv.dao.CustomMangaDao;
import com.anymv.dto.MangaSearchDTO;
import com.anymv.dto.mangaupdates.*;
import com.anymv.entity.Genre;
import com.anymv.entity.Image;
import com.anymv.entity.Manga;
import com.anymv.entity.Type;
import com.anymv.util.AnyMvPage;
import com.anymv.util.ErrorMessages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MangaService {
    @Autowired
    private CustomMangaDao customMangaDao;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ImageService imageService;

    private static ObjectMapper mapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(MangaService.class);


    @Transactional
    public AnyMvPage<Manga> search(MultiValueMap<String, Object> query) {
        MangaSearchDTO searchDTO = new MangaSearchDTO(query);
        TypedQuery<Manga> resultQuery = customMangaDao.findByFilter(searchDTO);

        if (resultQuery.getMaxResults() == 0) {
            try {
                return gatherResultsFromApi(searchDTO);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new AnyMvPage<>(resultQuery, searchDTO.getPage(), searchDTO.getItems());
        }

    }

    private AnyMvPage<Manga> gatherResultsFromApi(MangaSearchDTO searchDTO) throws IOException {
        MangaUpdatesResponse mangaUpdatesResponse = hitApi(searchDTO);
        List<Manga> results = cacheMangaUpdates(mangaUpdatesResponse);

        return new AnyMvPage<>(results, mangaUpdatesResponse.getTotalHits(), mangaUpdatesResponse.getPerPage(), mangaUpdatesResponse.getPage());
    }

    private MangaUpdatesResponse hitApi(MangaSearchDTO searchDTO) throws IOException {
        final String MANGA_UPDATES_API_SEARCH = "https://api.mangaupdates.com/v1/series/search";
        CloseableHttpClient client = HttpClients.createDefault();

        StringEntity entity = new StringEntity(
                mapper.writeValueAsString(createMangaUpdatesBody(searchDTO)),
                ContentType.APPLICATION_JSON
        );

        HttpPost post = new HttpPost(MANGA_UPDATES_API_SEARCH);
        post.setEntity(entity);

        CloseableHttpResponse response = client.execute(post);
        HttpEntity responseEntity = response.getEntity();

        return mapper.readValue(responseEntity.getContent(), MangaUpdatesResponse.class);
    }

    private List<Manga> cacheMangaUpdates(MangaUpdatesResponse mangaUpdatesResponse) {

        if (mangaUpdatesResponse.getTotalHits() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.RESOURCE_NOT_FOUND);
        }

        List<MangaUpdatesResult> results = mangaUpdatesResponse.getResults();
        List<Manga> dbManga = new ArrayList<>();

        for (MangaUpdatesResult result : results) {
            MangaUpdatesRecord record = result.getRecord();
            List<String> genres = mangaUpdatesGenreToStringArray(record.getGenres());

            List<Genre> dbGenres = genreService.getAndCreateGenres(genres);

            Manga manga = createMangaFromMangaUpdatesRecord(record);


        }

        return dbManga;

    }

    private Manga createMangaFromMangaUpdatesRecord(MangaUpdatesRecord record) {
        List<Genre> genres = genreService.getAndCreateGenres(mangaUpdatesGenreToStringArray(record.getGenres()));
        Image image = imageService.getOrCreateImage(record.getImage());

        Manga manga = new Manga();
        manga.setTitle(record.getTitle());
        manga.setYear(record.getYear());
        manga.setType(Type.MANGA);
        manga.setImage();

        return null;
    }

    private List<String> mangaUpdatesGenreToStringArray(List<MangaUpdatesGenre> genres) {
        ArrayList<String> genreStr = new ArrayList<>();

        for (MangaUpdatesGenre genre : genres) {
            genreStr.add(genre.getGenre());
        }

        return genreStr;
    }

    private MangaUpdatesRequest createMangaUpdatesBody(MangaSearchDTO searchDTO) {
        MangaUpdatesRequest request = new MangaUpdatesRequest();

        String search = searchDTO.getName();

        if (StringUtils.isEmpty(search)) {
            search = searchDTO.getDescription();
        }

        request.setPage(searchDTO.getPage());
        request.setPerPage(searchDTO.getItems());
        request.setGenres(searchDTO.getGenres());
        request.setSearch(search);

        return request;
    }

}
