package com.ray.model.dao;

import java.util.List;

import com.ray.model.entities.Tema;

public interface TemaRepository {

    Tema save(Tema tema);

    Tema update(Tema tema);

    void deleteById(Long id);

    Tema findById(Long id);

    List<Tema> findAll();

}
