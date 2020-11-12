package com.ray.model.dao;

import java.util.List;

import com.ray.model.entities.Arquivo;

public interface ImageRepository {

    Arquivo save(Arquivo arquivo);

    Arquivo update(Arquivo arquivo, boolean updateInputStream);

    void deleteById(Long id);

    Arquivo findById(Long id);

    List<Arquivo> findAll(Long canecaId);
}
