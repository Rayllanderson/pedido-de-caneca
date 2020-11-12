package com.ray.util;

import com.ray.model.entities.Arquivo;
import com.ray.model.service.ImageService;

public class ThreadMiniature implements Runnable {

    private ImageService imageService;
    private Arquivo arquivo;

    public ThreadMiniature(Arquivo arquivo) {
	this.arquivo = arquivo;
	this.imageService = new ImageService();
    }

    @Override
    public void run() {
	try {
	    boolean hasImage = arquivo.getId() != 0;
	    boolean hasntMiniature = arquivo.getMiniatura() == null || arquivo.getMiniatura().isEmpty();
	    boolean dontUpdateInpuStream = false;
	    if (hasImage && hasntMiniature) {
		System.out.println("Thread start");
		arquivo.setBase64(ArquivosUtil.createBase64(arquivo.getInputStream()));
		arquivo.setMiniatura(ArquivosUtil.createMiniatureBase64(arquivo.getBase64()));
		imageService.update(arquivo, dontUpdateInpuStream);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
