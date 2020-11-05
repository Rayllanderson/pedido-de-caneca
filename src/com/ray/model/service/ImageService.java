package com.ray.model.service;

import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Image;

public class ImageService {
    
    ImageRepository repository = RepositoryFactory.createImageDao();
    
    public Image save(Image image) {
	image.setBase64("");
	image.setMiniatura("");
	return repository.save(image);
    }
    
    public Image update(Image image) {
	return repository.update(image);
    }

}
