package com.anymv.service;

import com.anymv.dao.CustomMangaDao;
import com.anymv.dto.MangaSearchDTO;
import com.anymv.dto.mangaupdates.MangaUpdatesRequest;
import com.anymv.dto.mangaupdates.MangaUpdatesResponse;
import com.anymv.entity.Manga;
import com.anymv.util.AnyMvPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class MangaService {
    @Autowired
    private CustomMangaDao customMangaDao;

    private static ObjectMapper mapper = new ObjectMapper();


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

        MangaUpdatesResponse mangaUpdatesResponse = mapper.readValue(responseEntity.getContent(), MangaUpdatesResponse.class);

        return new AnyMvPage<>(cacheMangaUpdates(mangaUpdatesResponse), mangaUpdatesResponse.getTotalHits(), mangaUpdatesResponse.getPerPage(), mangaUpdatesResponse.getPage());
    }

    private List<Manga> cacheMangaUpdates(MangaUpdatesResponse mangaUpdatesResponse) {
        throw new NotImplementedException();
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
