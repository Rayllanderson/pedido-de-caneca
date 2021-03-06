package com.ray.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ray.model.dao.ClienteRepository;
import com.ray.model.dao.EntregaRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.dao.TemaRepository;
import com.ray.model.entities.Cliente;

@WebServlet("/save-client")
public class SaveClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ClienteRepository repository;
    private TemaRepository temaRepository = RepositoryFactory.createTemaDao();
    private EntregaRepository entregaRepository = RepositoryFactory.createEntregaDao();

    @Override
    public void init() throws ServletException {
	repository = RepositoryFactory.createClienteDao();
	super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	try {
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    String nome = request.getParameter("nome");
	    String telefone = request.getParameter("telefone");
	    Cliente cliente = new Cliente(null, nome, telefone);
	    cliente = repository.save(cliente);
	    request.getSession().setAttribute("temas", temaRepository.findAll());
	    request.getSession().setAttribute("entregas", entregaRepository.findAll());
	    // generate a new session
	    HttpSession newSession = request.getSession(true);
	    newSession.setMaxInactiveInterval(30 * 60); // 30 minutos
	    newSession.setAttribute("cliente", cliente);
	    response.setStatus(HttpServletResponse.SC_OK);
	    response.sendRedirect("order");
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println("from client controller");
	    request.getRequestDispatcher("error.jsp").forward(request, response);
	}
    }

}
