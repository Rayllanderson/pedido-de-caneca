package com.ray.model.service;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Arquivo;
import com.ray.model.entities.Caneca;

public class CanecaService {
    
    private CanecaRepository canecaRepository; 
    private ImageService imageService;
    private ImageRepository imgRepository;
    
    public CanecaService() {
	this.canecaRepository = RepositoryFactory.createCanecaDao();
	this.imageService = new ImageService();
	this.imgRepository = RepositoryFactory.createImageDao();
    }
    
    public Caneca save(Caneca caneca) {
	return canecaRepository.save(caneca);
    }
    
    public Caneca update(Caneca caneca) {
	return canecaRepository.update(caneca);
    }
    
    /**
     * Recupera todas as canecas do cliente, verifica se a caneca de fato pertence ao cliente, se pertencer, deleta e retorna true, senao, retorna false
     * Nota: Este m�todo tamb�m exlui todas imagens da caneca
     * @param id- id da caneca
     * @param clientId - id do cliente para verificar se a caneca pertence ao cliente
     */
    public boolean deleteById(Long id, Long clientId) {
	for(Caneca c : canecaRepository.findAll(clientId)) {
	    if (c.getId().equals(id)) {
		for(Arquivo image : imgRepository.findAll(c.getId())) {
		    imageService.deleteById(image.getId());
		}
		canecaRepository.deleteById(id);
		return true;
	    }
	}
	return false;
    }
}
