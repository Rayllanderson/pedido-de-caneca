package com.ray.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Cliente;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;
    
    private CanecaRepository canecaRepository = RepositoryFactory.createCanecaDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
	request.getSession().setAttribute("canecas", canecaRepository.findAll(cliente.getId()));
	System.out.println("w???");
	request.getRequestDispatcher("carrinho.jsp").forward(request, response);;
    }

}
