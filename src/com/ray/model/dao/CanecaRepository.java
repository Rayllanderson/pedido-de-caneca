package com.ray.model.dao;

import java.util.List;

import com.ray.model.entities.Caneca;

public interface CanecaRepository {

    Caneca save(Caneca caneca);

    Caneca update(Caneca caneca);

    void deleteById(Long id);

    Caneca findById(Long id);

    /**
     * 
     * @param clientId 
     * @return uma lista contendo todas as canecas do cliente informado via ID
     */
    List<Caneca> findAll(Long clientId);

}
