package com.ray.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;
import com.ray.model.service.CanecaService;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CanecaRepository canecaRepository;
    private CanecaService canecaService;

    @Override
    public void init() throws ServletException {
	canecaRepository = RepositoryFactory.createCanecaDao();
	canecaService = new CanecaService();
	super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
	String action = request.getParameter("action");
	List<Caneca> canecas = canecaRepository.findAll(cliente.getId());

	if (action != null) {
	    if (action.equals("load-miniature")) {
		loadMiniature(response, action, canecas);
		request.getSession().setAttribute("canecas", canecaRepository.findAll(cliente.getId()));
		response.setStatus(200);
		return;
	    }else if(action.equals("delete")) {
		delete(request, response, cliente);
	    }
	} else {
	    request.getSession().setAttribute("canecas", canecas);
	    request.getRequestDispatcher("carrinho.jsp").forward(request, response);
	}
    }


    /**
     * Enquanto a thread que cria miniatura não termina, ela irá buscar ela mesma no banco de dados pra verificar se a criação da miniatura já terminou.
     * @param response
     * @param action
     * @param canecas
     */
    private void loadMiniature(HttpServletResponse response, String action, List<Caneca> canecas ) {
	for (Caneca c : canecas) {
	    while (c.getImage().getMiniatura().equals("")) {
		c = canecaRepository.findById(c.getId());
	    }
	}
	
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response, Cliente cliente) {
   	Long id = Long.valueOf(request.getParameter("id1"));
   	canecaService.deleteById(id);
   	request.getSession().setAttribute("canecas", canecaRepository.findAll(cliente.getId()));
   	response.setStatus(200);
       }
}
