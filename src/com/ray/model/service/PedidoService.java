package com.ray.model.service;

import com.ray.model.dao.PedidoRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Pedido;

public class PedidoService {
    
    private PedidoRepository repository;
    
    public PedidoService() {
	this.repository = RepositoryFactory.createPedidoDao();
    }
    
    public boolean save(Pedido pedido) {
	return repository.save(pedido);
    }

}
