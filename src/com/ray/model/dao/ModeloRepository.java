package com.ray.model.dao;

import java.util.List;

import com.ray.model.entities.Modelo;

public interface ModeloRepository {

    Modelo save(Modelo modelo);

    Modelo update(Modelo modelo);

    void deleteById(Long id);

    Modelo findById(Long id);

    List<Modelo> findAll();

}
