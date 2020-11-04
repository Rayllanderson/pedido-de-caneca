package com.ray.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Cliente cliente;
    private List<Caneca> canecas = new ArrayList<>();

    public Pedido(Long id, Cliente cliente, List<Caneca> canecas) {
	super();
	this.id = id;
	this.cliente = cliente;
	this.canecas = canecas;
    }
    
    public Pedido() {};

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Cliente getCliente() {
	return cliente;
    }

    public void setCliente(Cliente cliente) {
	this.cliente = cliente;
    }

    public List<Caneca> getCanecas() {
	return canecas;
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
	Pedido other = (Pedido) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Pedido [id=" + id + ", cliente=" + cliente + ", canecas=" + canecas + "]";
    }

}
