package com.ray.model.entities;

import java.io.Serializable;
import com.ray.model.entities.enums.Etapa;

public class Caneca implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Integer quantidade;
    private Tema tema;
    private Etapa etapa;
    private Image image;
    private String descricao;
    
    private Cliente cliente;

    public Caneca(Long id, Integer quantidade, Tema tema, Etapa etapa, Image image, Cliente cliente, String descricao) {
	super();
	this.id = id;
	this.quantidade = quantidade;
	this.tema = tema;
	this.etapa = etapa;
	this.image = image;
	this.cliente = cliente;
	this.descricao = descricao;
    }
    
    public Caneca() {};

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

    public Etapa getEtapa() {
	return etapa;
    }

    public void setEtapa(Etapa etapa) {
	this.etapa = etapa;
    }

    public Image getImage() {
	return image;
    }

    public void setImage(Image image) {
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
	return "Caneca [id=" + id + ", quantidade=" + quantidade + ", tema=" + tema + ", modelo=" + etapa + ", foto="
		+ image + ", descricao= " + descricao + ", cliente = " + cliente + "]";
    }

    public Cliente getCliente() {
	return cliente;
    }

    public void setCliente(Cliente cliente) {
	this.cliente = cliente;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }
}
