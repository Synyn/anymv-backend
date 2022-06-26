package com.anymv.service;

import com.anymv.dto.MangaDto;
import com.anymv.dto.MangaResponseDto;
import com.anymv.dto.MangaSearchDTO;
import com.anymv.dto.mangaupdates.*;
import com.anymv.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class MangaUpdatesService {
    @Autowired
    private MangaService mangaService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private ImageService imageService;

    private final ObjectMapper mapper = new ObjectMapper();

    private final String MANGA_UPDATES_API_SEARCH = "https://api.mangaupdates.com/v1/series/search";

    public MangaResponseDto findManga(MangaSearchDTO searchDTO) throws IOException {
        MangaUpdatesResponse response = hitApi(searchDTO);

        return toMangaDto(response);
    }

    private MangaResponseDto toMangaDto(MangaUpdatesResponse response) {
        MangaResponseDto responseDto = new MangaResponseDto();

        responseDto.setPage(response.getPage());
        responseDto.setItemsPerPage(response.getPerPage());
        responseDto.setTotalResults(response.getTotalHits());
        responseDto.setRecords(toMangaRecords(response.getResults()));

        return responseDto;
    }

    private List<MangaDto> toMangaRecords(List<MangaUpdatesResult> results) {
        List<MangaDto> dtos = new ArrayList<>();

        for(MangaUpdatesResult result: results) {
            MangaUpdatesRecord record = result.getRecord();

            MangaDto dto = new MangaDto();
            dto.setTitle(record.getTitle());
            dto.setUpdatedDate(record.getLastUpdated().getAsRfc3339());
            dto.setDatePattern(Utils.RFC3339_PATTERN);
            dto.setTimeZone(ZoneId.of("PST"));
            dto.setImage(record.getImage().getUrl().getOriginal());
            dto.setGenres(toGenresStringArray(record.getGenres()));
            dto.setYear(record.getYear());

            dtos.add(dto);
        }

        return dtos;
    }

    private List<String> toGenresStringArray(List<MangaUpdatesGenre> genres) {
        List<String> stringGenres = new ArrayList<>();

        for(MangaUpdatesGenre genre: genres) {
            stringGenres.add(genre.getGenre());
        }

        return stringGenres;

    }

    private MangaUpdatesResponse hitApi(MangaSearchDTO searchDTO) throws IOException {
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
