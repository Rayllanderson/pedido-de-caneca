package com.ray.model.service;

import java.util.List;

import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Arquivo;

public class ImageService {

    ImageRepository repository = RepositoryFactory.createImageDao();

    public Arquivo save(Arquivo arquivo) {
	arquivo.setBase64("");
	arquivo.setMiniatura("");
	return repository.save(arquivo);
    }

    public Arquivo update(Arquivo arquivo, boolean updateInputStream) {
	return repository.update(arquivo, updateInputStream);
    }

    public void deleteById(Long id) {
	Long withoutImage = 0L;
	boolean hasImage = !id.equals(withoutImage);
	if (hasImage) {
	    repository.deleteById(id);
	}
    }
    
    /**
     * 
     * @param canecaId - todas as imagens de acordo com o id da caneca
     * @param withInputStream - <br>setar true para caso queira a lista completa, com todos os atributos. <br>Setar falso caso queira a lista parcialmente completa, sem inputstream, base64 e contentType
     * @return todas as canecas
     */
    public List<Arquivo> findAll(Long canecaId, boolean fullList){
	List<Arquivo> imagens = repository.findAll(canecaId);
	if (fullList) {
	    return imagens;
	}
	for(Arquivo image : imagens) {
	    image.setInputStream(null);
	    image.setBase64("");
	    image.setContentType("");
	}
	return imagens;
    }
}
