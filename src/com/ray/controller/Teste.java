package com.ray.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

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
	
	String action =  request.getParameter("action");
	Part filePart = request.getPart("pictureFile"); // Retrieves <input type="file" name="file">
	String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	InputStream fileContent = filePart.getInputStream();
	ImageRepository repository = RepositoryFactory.createImageDao();
	Image image = new Image(null, fileContent, "", "");
	//repository.save(image);
	image.setMiniatura(ArquivosUtil.createMiniatureBase64(fileContent));
	System.out.println(fileName);
	System.out.println(image.getMiniatura());
	String nome = request.getParameter("nome");
	System.out.println(nome);
	}

}
