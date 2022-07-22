package com.anymv.service;

import com.anymv.dao.AnimeDao;
import com.anymv.dao.AnimeTypeDao;
import com.anymv.dao.GenreDao;
import com.anymv.dao.SynonymDao;
import com.anymv.dto.animeoffline.AnimeOfflineDBResponse;
import com.anymv.dto.animeoffline.AnimeOfflineDbData;
import com.anymv.entity.*;
import com.anymv.util.ErrorMessages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

@Service
public class AnimeOfflineDBService {
    private final static String OFFLINE_DATABASE_URL = "https://raw.githubusercontent.com/manami-project/anime-offline-database/master/anime-offline-database.json";
    private static final int SUCCESS_CODE = 200;
    private static final ObjectMapper mapper = new ObjectMapper();


    private Logger logger = LoggerFactory.getLogger(AnimeOfflineDBService.class);

    @Autowired
    private AnimeDao animeDao;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private AnimeTypeDao animeTypeDao;

    @Autowired
    private SynonymDao synonymDao;


    public void updateDatabase() {
        try {
            AnimeOfflineDBResponse response = getData();
            handleUpdate(response);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

    }

    @Transactional
    private void handleUpdate(AnimeOfflineDBResponse response) {

        // TODO: Validate update
        logger.info("Started update ");
        List<AnimeOfflineDbData> dataList = response.getData();

        logger.info("Data size -> " + dataList.size());

        TreeMap<String, Anime> animeTree = getAllAnimes();

        for (AnimeOfflineDbData data : dataList) {
            Anime anime = animeTree.get(data.getTitle());
            if (anime == null) {
                createAnime(data);
            } else {
                updateAnime(anime, data);
            }
        }

        logger.info("Finished update");

    }

    @Transactional
    private void updateAnime(Anime anime, AnimeOfflineDbData data) {
        List<Genre> genres = handleGenres(data.getTags(), anime.getGenres());
        AnimeType type = getAnimeType(data.getType());

        if (!Objects.equals(anime.getTitle(), data.getTitle())) {
            anime.setTitle(data.getTitle());
        }

        if (!Objects.equals(anime.getImageUrl(), data.getPicture())) {
            anime.setImageUrl(data.getPicture());
        }

        if (!Objects.equals(anime.getYear(), data.getAnimeSeason().getYear())) {
            anime.setYear(data.getAnimeSeason().getYear());
        }

        if (!Objects.equals(anime.getSeason(), AnimeSeason.valueOf(data.getAnimeSeason().getSeason()))) {
            anime.setSeason(AnimeSeason.valueOf(data.getAnimeSeason().getSeason()));
        }

        if (!Objects.equals(anime.getThumbnailUrl(), data.getThumbnail())) {
            anime.setThumbnailUrl(data.getThumbnail());
        }

        if (!Objects.equals(anime.getStatus(), AnimeStatus.valueOf(data.getStatus()))) {
            anime.setStatus(AnimeStatus.valueOf(data.getStatus()));
        }

        if (!Objects.equals(anime.getEpisodes(), data.getEpisodes())) {
            anime.setEpisodes(data.getEpisodes());
        }

        List<Synonym> synonyms = anime.getSynonyms();
        for (String synonym : data.getSynonyms()) {
            Synonym found = synonyms.stream().filter(s -> StringUtils.equals(synonym, s.getName())).findFirst().orElse(null);

            if(found == null) {
                Synonym dbSynonym = createSynonym(synonym, anime);
                synonyms.add(dbSynonym);
            }
        }

        anime.setSynonyms(synonyms);
        anime.setType(type);
        anime.setGenres(genres);

        animeDao.save(anime);
    }

    @Transactional
    private AnimeType getAnimeType(String type) {
        AnimeType animeType = animeTypeDao.findByType(type);

        if (animeType == null) {
            animeType = animeTypeDao.save(createAnimeType(type));
        }

        return animeType;
    }

    private AnimeType createAnimeType(String type) {
        AnimeType animeType = new AnimeType();
        animeType.setType(type);

        return animeTypeDao.save(animeType);
    }

    @Transactional
    private List<Genre> handleGenres(List<String> tags, List<Genre> genres) {
        for (String tag : tags) {
            Genre genre
                    = genres.stream().filter(g -> StringUtils.equals(tag, g.getGenre())).findFirst().orElse(null);

            if (genre == null) {
                genre = createNewGenre(tag);

                genres.add(genre);
            }
        }

        return genres;
    }

    private Genre createNewGenre(String tag) {
        Genre genre = new Genre();
        genre.setGenre(tag);

        return genreDao.save(genre);
    }

    @Transactional
    private void createAnime(AnimeOfflineDbData data) {
        Anime anime = animeDao.save(new Anime());

        List<Genre> genres = getGenres(data.getTags());
        List<Synonym> synonyms = getSynonyms(data.getSynonyms(), anime);
        AnimeType animeType = getAnimeType(data.getType());

        anime.setTitle(data.getTitle());
        anime.setImageUrl(data.getPicture());
        anime.setEpisodes(data.getEpisodes());
        anime.setThumbnailUrl(data.getThumbnail());
        anime.setYear(data.getAnimeSeason().getYear());

        anime.setSeason(AnimeSeason.valueOf(data.getAnimeSeason().getSeason()));
        anime.setStatus(AnimeStatus.valueOf(data.getStatus()));

        anime.setGenres(genres);
        anime.setType(animeType);
        anime.setSynonyms(synonyms);

        animeDao.save(anime);
    }

    private List<Synonym> getSynonyms(List<String> synonyms, Anime anime) {
        List<Synonym> newSynonyms = new ArrayList<>();

        for (String synonym : synonyms) {
            Synonym dbSynonym = createSynonym(synonym, anime);

            newSynonyms.add(dbSynonym);
        }

        return newSynonyms;
    }

    private Synonym createSynonym(String synonym, Anime anime) {
        Synonym dbSynonym = new Synonym();
        dbSynonym.setName(synonym);
        dbSynonym.setAnime(anime);

        return synonymDao.save(dbSynonym);
    }

    @Transactional
    private List<Genre> getGenres(List<String> tags) {
        List<Genre> genres = new ArrayList<>();

        List<Genre> dbGenres = genreDao.findAll();

        for (String tag : tags) {
            Genre genre
                    = dbGenres.stream().filter(g -> StringUtils.equals(g.getGenre(), tag)).findFirst().orElse(null);

            if (genre == null) {
                genre = createNewGenre(tag);
            }

            genres.add(genre);
        }

        return genres;

    }

    @Transactional
    private TreeMap<String, Anime> getAllAnimes() {
        TreeMap<String, Anime> tree = new TreeMap<>();

        List<Anime> animeList = animeDao.findAll();

        for (Anime anime : animeList) {
            tree.put(anime.getTitle(), anime);
        }

        return tree;
    }

    private AnimeOfflineDBResponse getData() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(OFFLINE_DATABASE_URL);

        CloseableHttpResponse response = client.execute(httpGet);


        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != SUCCESS_CODE) {
            throw new RuntimeException(ErrorMessages.ERROR_GETTING_MANGA_OFFLINE_DB);
        }

        InputStream json = response.getEntity().getContent();

        return mapper.readValue(json, AnimeOfflineDBResponse.class);
    }
}
