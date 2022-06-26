package com.anymv.service;

import com.anymv.dao.ImageDao;
import com.anymv.dto.mangaupdates.MangaUpdatesImage;
import com.anymv.dto.mangaupdates.MangaUpdatesImageUrl;
import com.anymv.entity.Image;
import com.anymv.util.Utils;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

@Service
public class ImageService {

    @Autowired
    private ImageDao imageDao;

    @Transactional
    public Image getOrCreateImage(MangaUpdatesImage image) {
        String hashSha256 =
                Utils.hashSha256(image.getUrl().getOriginal() + image.getUrl().getThumb());

        Image dbImage = imageDao.findOneByHash(hashSha256);
        if (dbImage == null) {
            dbImage = createNewImage(image.getUrl());
        }

        return dbImage;
    }

    @Transactional
    private Image createNewImage(MangaUpdatesImageUrl url) {
        Image image = new Image();
        image.setImagePath(url.getOriginal());
        image.setHash(Utils.hashSha256(url.getOriginal() + url.getThumb()));

        return imageDao.save(image);
    }
}
