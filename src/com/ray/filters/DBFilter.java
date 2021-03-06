package com.ray.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.ray.db.DB;

@WebFilter(urlPatterns = { "/*" }) // toda requisi��o vai passar pelo filter
public class DBFilter implements javax.servlet.Filter {
//    private HttpServletRequest req; // convertendo o request
//    private HttpSession session;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	try {
//	    req = (HttpServletRequest) request;
//	    session = req.getSession();
	    request.setCharacterEncoding("UTF-8");
	    chain.doFilter(request, response);
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println("from filter");
	    request.getRequestDispatcher("error.jsp").forward(request, response);
	}
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
	try{
	    DB.getConnection();
	}catch (Exception e) {
	    DB.getConnection(); //tentando acordar a conex�o... 
	}
    }

    @Override
    public void destroy() {
    }
    
    
}
