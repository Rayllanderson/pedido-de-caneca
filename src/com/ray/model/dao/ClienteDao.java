package com.ray.model.dao;

import java.util.List;

import com.ray.model.entities.Cliente;

public interface ClienteDao {

    Cliente save(Cliente cliente);

    Cliente update(Cliente cliente);

    void deleteById(Long id);

    Cliente findById(Long id);

    List<Cliente> findAll();

}
