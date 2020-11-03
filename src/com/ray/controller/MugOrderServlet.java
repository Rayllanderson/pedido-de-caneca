package com.ray.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.ray.util.ArquivosUtil;

/**
 * mudar o nome também
 */
@MultipartConfig
@WebServlet("/order")
public class MugOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MugOrderServlet() {
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
	String tema = request.getParameter("tema");
	String descricao = request.getParameter("descricao");
	String foto64 = ArquivosUtil.createBase64(request);
	
	    String description = request.getParameter("descricao"); // Retrieves <input type="text" name="description">
	    Part filePart = request.getPart("foto"); // Retrieves <input type="file" name="file">
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream fileContent = filePart.getInputStream();
	    
	    
//	    System.out.println(fileContent);
	
	
	request.getRequestDispatcher("resumo.jsp").forward(request, response);
	
	System.out.println(tema);
	System.out.println(descricao);
    }

}
