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

import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Image;
import com.ray.util.ArquivosUtil;

@MultipartConfig
@WebServlet("/testea")
public class Teste extends HttpServlet{

    private static final long serialVersionUID = 1L;
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
	// separar por action;
	// foto será um atributo da classe;
	// foto criará a miniatura via ajax;
	// na hora do grande envio (vou saber via action) verificar se a imagem é a mesma;
	// se for a mesma, deixa estar, senao, cria nova miniatura;
	
	String action =  request.getParameter("action");
	Part filePart = request.getPart("pictureFile"); // Retrieves <input type="file" name="file">
	String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	InputStream fileContent = filePart.getInputStream();
	
//	Image image = new Image(null, fileContent, "", "");
//	//repository.save(image);
//	image.setMiniatura(ArquivosUtil.createMiniatureBase64(fileContent));
//	System.out.println(fileName);
//	System.out.println(image.getMiniatura());
//	String nome = request.getParameter("nome");
//	System.out.println(nome);
	}

}
