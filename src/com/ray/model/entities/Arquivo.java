package com.ray.model.entities;

import java.io.InputStream;
import java.io.Serializable;

public class Arquivo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private InputStream inputStream;
    private String base64;
    private String miniatura;
    private String contentType;
    private Caneca caneca;
    private String nome; // pra pegar o contentType caso necessário

    public Arquivo(Long id, InputStream inputStream, String base64, String miniatura, String contentType,
	    Caneca caneca, String nome) {
	this.id = id;
	this.inputStream = inputStream;
	this.base64 = base64;
	this.miniatura = miniatura;
	this.contentType = contentType;
	this.caneca = caneca;
	this.nome = nome;
    }
    
    public Arquivo(Long id, InputStream inputStream, String base64, String miniatura, String contentType,
	    Caneca caneca) {
	this.id = id;
	this.inputStream = inputStream;
	this.base64 = base64;
	this.miniatura = miniatura;
	this.contentType = contentType;
	this.caneca = caneca;
    }

    public Arquivo() {
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

    public Caneca getCaneca() {
	return caneca;
    }

    public void setCaneca(Caneca caneca) {
	this.caneca = caneca;
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
	Arquivo other = (Arquivo) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Arquivo [id=" + id + "base64=" + getBase64Html();
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

}
