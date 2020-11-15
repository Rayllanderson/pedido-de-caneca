package com.ray.util;


import com.ray.model.entities.Arquivo;
import com.ray.model.service.ImageService;

public class ThreadMiniature implements Runnable {

    private ImageService imageService;
    private Arquivo foto;

    /**
     * Cria a base 64 e miniatura do arquivo. Após completar, da update
     * @param imagem
     */
    public ThreadMiniature(Arquivo imagem) {
	this.foto = imagem;
	this.imageService = new ImageService();
	Thread t = new Thread(this);
	t.start();
    }

    @Override
    public void run() {
	try {
	    boolean hasImage = foto.getId() != 0;
	    boolean hasntMiniature = foto.getMiniatura() == null || foto.getMiniatura().isEmpty();
	    boolean dontUpdateInpuStream = false;
	    if (hasImage && hasntMiniature) {
		System.out.println("Thread start");
		foto.setBase64(ArquivosUtil.createBase64(foto.getInputStream()));
		foto.setMiniatura(ArquivosUtil.createMiniatureBase64(foto.getBase64()));
		imageService.update(foto, dontUpdateInpuStream);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
