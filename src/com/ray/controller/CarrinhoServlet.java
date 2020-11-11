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
	List<Caneca> canecas = canecaService.findAll(cliente.getId(), false);
	setQuantidadeCanecas(request, canecas);
	if (action != null) {
	    if (action.equals("load-miniature") && thumbIsLoading(canecas)) {
		loadThumb(response, action, canecas);
		response.setStatus(200);
		return;
	    } else if (action.equals("delete")) {
		delete(request, response, cliente.getId());
	    }
	} else {
	    request.getSession().setAttribute("canecas", canecas);
	    request.getRequestDispatcher("carrinho.jsp").forward(request, response);
	}
    }

    /**
     * Se alguma thumb estiver com valor vazio (ou seja, carregando), return true;
     * @param canecas
     * @return
     */
    private boolean thumbIsLoading(List<Caneca> canecas) {
	for (Caneca c : canecas) {
	    if(c.getImage().getMiniatura().equals("")) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Enquanto a thread que cria miniatura não termina, ela irá buscar ela mesma no
     * banco de dados pra verificar se a criação da miniatura já terminou.
     * 
     * @param response
     * @param action
     * @param canecas
     * @throws IOException 
     */
    private void loadThumb(HttpServletResponse response, String action, List<Caneca> canecas) throws IOException {
	for (Caneca c : canecas) {
	    while (c.getImage().getMiniatura().equals("")) {
		c = canecaRepository.findById(c.getId());
	    }
	}
    }

    private void delete(HttpServletRequest request, HttpServletResponse response, Long clienteId) {
	Long id = Long.valueOf(request.getParameter("id1"));
	if (canecaService.deleteById(id, clienteId)) {
//	    List<Caneca> canecas = canecaRepository.findAll(clienteId);
//	    request.getSession().setAttribute("canecas", canecas);
//	    request.getSession().setAttribute("size", canecas.size());
	    response.setStatus(204);
	}else {
	    response.setStatus(404);
	}
    }
    
    private void setQuantidadeCanecas(HttpServletRequest request, List<Caneca> canecas) {
	request.getSession().setAttribute("size", canecas.size());
	if(canecas.size() == 1) {
	    request.getSession().setAttribute("txt", "Caneca");
	}else {
	    request.getSession().setAttribute("txt", "Canecas");
	}
    }
}
