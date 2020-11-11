package com.ray.model.service;

import java.util.List;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Caneca;

public class CanecaService {
    
    private CanecaRepository canecaRepository; 
    private ImageService imageService;
    
    public CanecaService() {
	this.canecaRepository = RepositoryFactory.createCanecaDao();
	this.imageService = new ImageService();
    }
    
    public Caneca save(Caneca caneca) {
	return canecaRepository.save(caneca);
    }
    
    public Caneca update(Caneca caneca) {
	return canecaRepository.update(caneca);
    }
    
    /**
     * Recupera todas as canecas do cliente, verifica se a caneca de fato pertence ao cliente, se pertencer, deleta e retorna true, senao, retorna false
     * Nota: Este método também exlui a imagem da caneca
     * @param id- id da caneca
     * @param clientId - id do cliente para verificar se a caneca pertence ao cliente
     */
    public boolean deleteById(Long id, Long clientId) {
	for(Caneca c : canecaRepository.findAll(clientId)) {
	    if (c.getId().equals(id)) {
		canecaRepository.deleteById(id);
		imageService.deleteById(c.getImage().getId());
		return true;
	    }
	}
	return false;
    }
    
    /**
     * 
     * @param clienteId - todas as canecas de acordo com o id do cliente
     * @param withInputStream - <br>setar true para caso queira a lista completa, com todos os atributos. <br>Setar falso caso queira a lista parcialmente completa, sem inputstream, base64 e contentType
     * @return todas as canecas
     */
    public List<Caneca> findAll(Long clienteId, boolean withInputStream){
	List<Caneca> canecas = canecaRepository.findAll(clienteId);
	if(withInputStream) {
	    return canecas;
	}
	
	for(Caneca c : canecas) {
	    c.getImage().setInputStream(null);
	    c.getImage().setBase64(null);
	    c.getImage().setContentType(null);
	}
	return canecas;
    }
}
