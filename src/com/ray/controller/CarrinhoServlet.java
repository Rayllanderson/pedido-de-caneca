package com.ray.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;
import com.ray.model.service.CanecaService;
import com.ray.util.ArquivosUtil;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CanecaRepository canecaRepository;
    private CanecaService canecaService;
    private ImageRepository imgRepository;

    @Override
    public void init() throws ServletException {
	canecaRepository = RepositoryFactory.createCanecaDao();
	canecaService = new CanecaService();
	imgRepository = RepositoryFactory.createImageDao();
	super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
	String action = request.getParameter("action");
	List<Caneca> canecas = canecaRepository.findAll(cliente.getId());
	canecas.forEach(x -> x.getFotos().add(ArquivosUtil.getFirstImage(x.getId()))); 
//	canecas.stream().filter(x -> x.getFotos().isEmpty()).forEach(x -> x.getFotos().add(imgRepository.findById(0L))); //caneca sem foto
	setQuantidadeCanecas(request, canecas);
	if (action != null) {
	    if (action.equals("load-miniature") && ArquivosUtil.thumbIsLoading(canecas)) {
		canecas.forEach(x -> ArquivosUtil.loadThumb(x.getFotos(), false, imgRepository));
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

    private void delete(HttpServletRequest request, HttpServletResponse response, Long clienteId) {
	Long id = Long.valueOf(request.getParameter("id1"));
	if (canecaService.deleteById(id, clienteId)) {
	    response.setStatus(204);
	} else {
	    response.setStatus(404);
	}
    }

    private void setQuantidadeCanecas(HttpServletRequest request, List<Caneca> canecas) {
	request.getSession().setAttribute("size", canecas.size());
	if (canecas.size() == 1) {
	    request.getSession().setAttribute("txt", "Caneca");
	} else {
	    request.getSession().setAttribute("txt", "Canecas");
	}
    }
}
