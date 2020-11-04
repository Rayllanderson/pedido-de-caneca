package com.ray.model.dao;

import java.util.List;

import com.ray.model.entities.Image;

public interface ImageDao {

    Image save(Image image);

    Image update(Image image);

    void deleteById(Long id);

    Image findById(Long id);

    List<Image> findAll();

}
