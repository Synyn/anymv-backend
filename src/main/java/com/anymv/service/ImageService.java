package com.anymv.service;

import com.anymv.dao.ImageDao;
import com.anymv.entity.Image;
import com.anymv.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ImageService {

    @Autowired
    private ImageDao imageDao;

    @Transactional
    public Image getOrCreateImage(String imageUrl) {
        Image dbImage = imageDao.findOneByImagePath(imageUrl);
        if (dbImage == null) {
            dbImage = createNewImage(imageUrl);
        }

        return dbImage;
    }

    @Transactional
    private Image createNewImage(String imageUrl) {
        Image image = new Image();
        image.setImagePath(imageUrl);

        return imageDao.save(image);
    }
}
