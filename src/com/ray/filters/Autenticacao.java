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



@WebFilter(urlPatterns = { "/categorias/*", "/produtos/*", "/all-products/*", "/all-products.jsp/*", "/home.jsp", "/home", "/categorias.jsp", "/produtos.jsp", "/account.jsp", "/my-account/*", "/estatisticas/*", "/estatisticas.jsp" })
public class Autenticacao implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//	Filter.super.init(filterConfig);
	this.context = filterConfig.getServletContext();
	this.context.log("AuthenticationFilter initialized");
    }

//    // intercepta todas as requisi��es
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//	    throws IOException, ServletException {
//	HttpServletRequest req = (HttpServletRequest) request;
//	HttpServletResponse res = (HttpServletResponse) response;
////	User user = (User) req.getSession().getAttribute("user");
//	setUserTheme(req, user);
//	HttpSession session = req.getSession(false);
//	request.setCharacterEncoding("UTF-8");
////	System.out.println("Ola eu sou o filter passando por aqui. session = " + session);
//	if (session == null || user == null) { // checking whether the session exists
//	    this.context.log("Unauthorized access request");
//	    res.sendRedirect(req.getContextPath() + "/welcome.jsp");
//	} else {
//	    // pass the request along the filter chain
//	    chain.doFilter(request, response);
//	}
//    }

    @Override
    public void destroy() {
//	Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
	    throws IOException, ServletException {
	// TODO Auto-generated method stub
	
    }
    
  
}
