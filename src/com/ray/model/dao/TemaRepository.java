package com.ray.model.dao;

import java.util.List;

import com.ray.model.entities.Tema;

public interface TemaRepository {

    Tema findById(Long id);

    List<Tema> findAll();
}
