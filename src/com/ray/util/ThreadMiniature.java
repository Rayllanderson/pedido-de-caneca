package com.ray.util;

import com.ray.model.entities.Image;
import com.ray.model.service.ImageService;

public class ThreadMiniature implements Runnable {

    private ImageService imageService;
    private Image image;

    public ThreadMiniature(Image image) {
	this.image = image;
	this.imageService = new ImageService();
    }

    @Override
    public void run() {
	try {
	    boolean hasImage = image.getId() != 0;
	    boolean hasntMiniature = image.getMiniatura() == null || image.getMiniatura().isEmpty();
	    boolean dontUpdateInpuStream = false;
	    if (hasImage && hasntMiniature) {
		System.out.println("Thread start");
		image.setBase64(ArquivosUtil.createBase64(image.getInputStream()));
		image.setMiniatura(ArquivosUtil.createMiniatureBase64(image.getBase64()));
		imageService.update(image, dontUpdateInpuStream);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
