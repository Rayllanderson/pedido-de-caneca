package com.ray.model.listeners;

import java.util.List;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;
import com.ray.model.service.CanecaService;
import com.ray.model.service.ClienteService;

@WebListener
public class SessionOutListener implements HttpSessionListener {

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
	ClienteService clientService = new ClienteService();
	CanecaService canecaService = new CanecaService();

	Cliente cliente = (Cliente) se.getSession().getAttribute("cliente");
	@SuppressWarnings("unchecked")
	List<Caneca> canecas = (List<Caneca>) se.getSession().getAttribute("canecas");
	
	for (Caneca c : canecas) {
	    canecaService.deleteById(c.getId(), cliente.getId());
	}
	clientService.deleteById(cliente.getId());
	HttpSessionListener.super.sessionDestroyed(se);
    }

}
