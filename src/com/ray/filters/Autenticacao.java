package com.ray.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ray.model.entities.Cliente;



@WebFilter(urlPatterns = { "/order.jsp", "/order/*", "/edit-order.jsp", "/carrinho.jsp", "/carrinho/*"})
public class Autenticacao implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//	Filter.super.init(filterConfig);
	this.context = filterConfig.getServletContext();
	this.context.log("AuthenticationFilter initialized");
    }

    // intercepta todas as requisi��es
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse res = (HttpServletResponse) response;
	Cliente cliente = (Cliente) req.getSession().getAttribute("cliente");
	HttpSession session = req.getSession(false);
	request.setCharacterEncoding("UTF-8");
//	System.out.println("Ola eu sou o filter passando por aqui. session = " + session);
	if (session == null || cliente == null) { // checking whether the session exists
	    this.context.log("Unauthorized access request");
	    res.sendRedirect(req.getContextPath() + "/index.jsp");
	} else {
	    // pass the request along the filter chain
	    chain.doFilter(request, response);
	}
    }
}
