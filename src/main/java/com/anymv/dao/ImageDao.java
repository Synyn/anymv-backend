package com.anymv.dao;

import com.anymv.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageDao extends JpaRepository<Image, Long> {
    @Query("SELECT image FROM Image image WHERE image.imagePath = ?1")
    public Image findOneByImagePath(String imagePath);
}
