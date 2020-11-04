package com.ray.model.entities;

public class Caneca {

    private Long id;
    private Integer quantidade;
    private Tema tema;
    private Modelo modelo;
    private Image image;

    public Caneca(Long id, Integer quantidade, Tema tema, Modelo modelo, Image image) {
	super();
	this.id = id;
	this.quantidade = quantidade;
	this.tema = tema;
	this.modelo = modelo;
	this.image = image;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Integer getQuantidade() {
	return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
	this.quantidade = quantidade;
    }

    public Tema getTema() {
	return tema;
    }

    public void setTema(Tema tema) {
	this.tema = tema;
    }

    public Modelo getModelo() {
	return modelo;
    }

    public void setModelo(Modelo modelo) {
	this.modelo = modelo;
    }

    public Image getFoto() {
	return image;
    }

    public void setFoto(Image image) {
	this.image = image;
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
	Caneca other = (Caneca) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Caneca [id=" + id + ", quantidade=" + quantidade + ", tema=" + tema + ", modelo=" + modelo + ", foto="
		+ image + "]";
    }
}
