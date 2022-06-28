package com.anymv.service;

import com.anymv.dao.CustomMangaDao;
import com.anymv.dao.MangaDao;
import com.anymv.dto.MangaDto;
import com.anymv.dto.MangaResponseDto;
import com.anymv.dto.MangaSearchDTO;
import com.anymv.dto.mangaupdates.*;
import com.anymv.entity.Manga;
import com.anymv.entity.Type;
import com.anymv.util.AnyMvPage;
import com.anymv.util.ErrorMessages;
import com.anymv.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;

@Service
public class MangaService {
    @Autowired
    private CustomMangaDao customMangaDao;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MangaUpdatesService mangaUpdatesService;

    @Autowired
    private MangaDao mangaDao;

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
        MangaResponseDto response = mangaUpdatesService.findManga(searchDTO);
        if (response.getTotalResults() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.RESOURCE_NOT_FOUND);
        }

        List<Manga> results = cacheApiResponse(response.getRecords());
        return new AnyMvPage<>(results, response.getTotalResults(), response.getItemsPerPage(), response.getPage());
    }


    @Transactional
    private List<Manga> cacheApiResponse(List<MangaDto> records) {
        List<Manga> caches = new ArrayList<>();

        for(MangaDto record: records) {
            Manga manga = createMangaFromDto(record);

            manga.setImage(imageService.getOrCreateImage(record.getImage()));
            manga.setGenres(genreService.getAndCreateGenres(record.getGenres()));

            caches.add(manga);
        }

        return mangaDao.saveAll(caches);

    }

    /**
     * This will only add the basic parameters.
     *
     * The images and genres have to be set up from a different space.
     * @param dto - The dto that we need to take the data from
     * @return Manga - the record that should be set in the db
     *
     * NOTE: This method will not attach or create the record, it will only model it.
     */
    private Manga createMangaFromDto(MangaDto dto) {
        Manga manga = new Manga();
        manga.setDescription(dto.getDescription());
        manga.setTitle(dto.getTitle());
        manga.setYear(dto.getYear());
        manga.setType(dto.getType());
        manga.setUpdated(Utils.stringToDateUTC(dto.getUpdatedDate(), dto.getDatePattern(), dto.getTimeZone()));

        return manga;
    }

    /**
     * Finds one manga by id.
     * @param id Long - the id of the manga
     * @return Manga - the resource
     * @throws ResponseStatusException - if the resource was not found.
     */
    public Manga findOneById(Long id) {
        Optional<Manga> manga = mangaDao.findById(id);

        boolean present = manga.isPresent();
        if(!present) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.RESOURCE_NOT_FOUND);
        }

        return manga.get();
    }

    /**
     * Find newest
     * @param type Type - the type of manga that we need to get
     * @param limit int - how many records we need
     * @return List<Manga> - the found resources
     */
    public List<Manga> getNewest(Type type, int limit) {
        List<Manga> newest = mangaDao.findNewest(type, limit);

        if(newest.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.RESOURCE_NOT_FOUND);
        }

        return newest;
    }
}
