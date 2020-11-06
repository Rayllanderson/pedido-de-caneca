package com.ray.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ray.model.entities.Cliente;

@WebServlet("/save-client")
public class SaveClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SaveClientServlet() {
	super();
	// TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub
	response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String nome = request.getParameter("nome");
	String telefone = request.getParameter("telefone");
	Cliente cliente = new Cliente(null, nome, telefone); //salvar o cliente on db
	response.setContentType("text/plain");
	response.setCharacterEncoding("UTF-8");
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
