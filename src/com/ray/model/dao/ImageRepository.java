package com.ray.model.dao;

import java.util.List;

import com.ray.model.entities.Image;

public interface ImageRepository {

    Image save(Image image);

    Image update(Image image, boolean updateInputStream);

    void deleteById(Long id);

    Image findById(Long id);

    List<Image> findAll();
}
