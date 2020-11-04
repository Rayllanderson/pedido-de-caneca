package com.ray.model.entities;

import java.io.InputStream;

public class Foto {
    
    private InputStream inputStream;
    private String base64;
    private String miniatura;
    
    public Foto(InputStream inputStream, String base64) {
	super();
	this.inputStream = inputStream;
	this.base64 = base64;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getMiniatura() {
        return miniatura; //ainda tenho que dar um get correto aqui...
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    @Override
    public String toString() {
	return "Foto [base64=" + base64 + "]";
    }
}
