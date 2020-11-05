package com.ray.model.service;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Caneca;

public class CanecaService {
    
    private CanecaRepository repository = RepositoryFactory.createCanecaDao();
    
    
    public Caneca save(Caneca caneca) {
	return repository.save(caneca);
    }
    
    public Caneca update(Caneca caneca) {
	return repository.update(caneca);
    }
    
    public void deleteById(Long id) {
	repository.deleteById(id);
    }
    
    

}
