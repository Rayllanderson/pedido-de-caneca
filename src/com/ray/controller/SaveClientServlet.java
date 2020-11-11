package com.ray.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ray.model.dao.ClienteRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Cliente;

@WebServlet("/save-client")
public class SaveClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ClienteRepository repository;
    
    public SaveClientServlet() {
	super();
	// TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
	repository = RepositoryFactory.createClienteDao();
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub
	response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/plain");
	response.setCharacterEncoding("UTF-8");
	String nome = request.getParameter("nome");
	String telefone = request.getParameter("telefone");
	Cliente cliente = new Cliente(null, nome, telefone); //salvar o cliente on db
	cliente = repository.save(cliente);
//	// invalidando a ultima sessão / fazer isso no final
//	HttpSession oldSession = request.getSession(false);
//	if (oldSession != null) {
//	oldSession.invalidate();
//	}

	// generate a new session
	HttpSession newSession = request.getSession(true);
	newSession.setMaxInactiveInterval(30 * 60); //30 minutos
	newSession.setAttribute("cliente", cliente);
	response.setStatus(HttpServletResponse.SC_OK);
	response.sendRedirect("order");
    }

}
