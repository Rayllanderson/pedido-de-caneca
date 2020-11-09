package com.ray.util;

import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Image;

public class ThreadMiniature implements Runnable {

    private ImageRepository imageRepository;
    private Image image;

    public ThreadMiniature(Image image) {
	this.image = image;
	this.imageRepository = RepositoryFactory.createImageDao();
    }

    @Override
    public void run() {
	try {
	    boolean hasImage = image.getId() != 0;
	    if (hasImage) {
		System.out.println("Thread start");
		image.setBase64(ArquivosUtil.createBase64(image.getInputStream()));
		image.setMiniatura(ArquivosUtil.createMiniatureBase64(image.getBase64()));
		imageRepository.update(image);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
