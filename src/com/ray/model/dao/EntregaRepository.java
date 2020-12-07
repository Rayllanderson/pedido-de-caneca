package com.ray.model.dao;

import java.util.List;

import com.ray.model.entities.Entrega;


public interface EntregaRepository {

    Entrega findById(Long id);

    List<Entrega> findAll();
}
