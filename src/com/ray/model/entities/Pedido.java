package com.ray.model.entities;

import java.io.Serializable;
import java.util.Date;

import com.ray.util.DateUtil;

public class Pedido implements Serializable{

    private static final long serialVersionUID = 1L;
    private Cliente cliente;
    private Date data;
    private Entrega entrega;

    public Pedido(Cliente cliente, Date data, Entrega entrega) {
	this.cliente = cliente;
	this.data = data;
	this.entrega = entrega;
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

    public Entrega getEntrega() {
	return entrega;
    }

    public void setEntrega(Entrega entrega) {
	this.entrega = entrega;
    }
    
}
