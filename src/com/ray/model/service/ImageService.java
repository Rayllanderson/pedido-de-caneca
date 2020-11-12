package com.ray.model.service;

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

}
