package com.ray.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ray.model.entities.Cliente;

@WebServlet("/save-client")
public class SaveClientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveClientServlet() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// TODO Auto-generated method stub
	response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String nome = request.getParameter("nome");
	String telefone = request.getParameter("telefone");
	Cliente cliente = new Cliente(null, nome, telefone);
	response.setContentType("text/plain");
	response.setCharacterEncoding("UTF-8");
//	// invalidando a ultima sessão / fazer isso no final
//	HttpSession oldSession = request.getSession(false);
//	if (oldSession != null) {
//	oldSession.invalidate();
//	}

	// generate a new session
	HttpSession newSession = request.getSession(true);
	newSession.setMaxInactiveInterval(30 * 60);
	newSession.setAttribute("cliente", cliente);
	response.setStatus(HttpServletResponse.SC_OK);
	response.sendRedirect("order.jsp");
    }

}
