package com.ray.model.entities;

import java.io.InputStream;
import java.io.Serializable;

public class Image implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private InputStream inputStream;
    private String base64;
    private String miniatura;
    private String contentType;

    public Image(Long id, InputStream inputStream, String base64, String miniatura, String contentType) {
	this.id = id;
	this.inputStream = inputStream;
	this.base64 = base64;
	this.miniatura = miniatura;
	this.contentType = contentType;
    }
    
    public Image() {
	// TODO Auto-generated constructor stub
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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
	return miniatura; // ainda tenho que dar um get correto aqui...
    }

    public void setMiniatura(String miniatura) {
	this.miniatura = miniatura;
    }

    public String getContentType() {
	return contentType;
    }

    public void setContentType(String contentType) {
	this.contentType = contentType;
    }
    
    public String getBase64Html() {
  	return base64.isEmpty() ? "" : "data:" + this.getContentType() + ";base64," + base64;
      }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Image other = (Image) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Foto [base64=" + base64 + "]";
    }
}
