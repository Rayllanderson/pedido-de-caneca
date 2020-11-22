package com.ray.model.entities;

import java.io.Serializable;
import java.util.Date;

import com.ray.util.DateUtil;

public class Pedido implements Serializable{

    private static final long serialVersionUID = 1L;
    private Cliente cliente;
    private Date data;

    public Pedido(Cliente cliente, Date data) {
	super();
	this.cliente = cliente;
	this.data = data;
    }

    public Cliente getCliente() {
	return cliente;
    }

    public void setCliente(Cliente cliente) {
	this.cliente = cliente;
    }

    public Date getData() {
	return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public String getDataString() {
	return DateUtil.formatData(data);
    }
}
