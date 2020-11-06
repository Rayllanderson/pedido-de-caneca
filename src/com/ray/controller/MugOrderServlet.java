package com.ray.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Image;
import com.ray.util.ArquivosUtil;

/**
 * mudar o nome também
 */
@MultipartConfig
@WebServlet("/order")
public class MugOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ImageRepository imageRepository;
    private Image image = new Image();
    byte[] result;

    @Override
    public void init() throws ServletException {
	imageRepository = RepositoryFactory.createImageDao();
	super.init();
    }

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

	String action = request.getParameter("action");
	System.out.println(action);
	if (action != null) {
	    if (action.equals("process-picture")) { // via ajax
		Part filePart = request.getPart("pictureFile"); // Retrieves <input type="file" name="pictureFile">
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		InputStream fileContent = filePart.getInputStream();
		result = new byte[fileContent.available()];
		image.setInputStream(fileContent);
		image.setBase64(ArquivosUtil.createBase64(fileContent));
		image.setMiniatura(ArquivosUtil.createMiniatureBase64(image.getBase64()));

		System.out.println(image.getBase64());
		System.out.println(image.getMiniatura());
		System.out.println(fileName);
	    } else if (action.equals("next-page")) {
		String tema = request.getParameter("tema");
		String descricao = request.getParameter("descricao");

		Part filePart = request.getPart("pictureFile"); // Retrieves <input type="file" name="pictureFile">
		InputStream fileContent = filePart.getInputStream();
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		byte[] result2 = new byte[fileContent.available()];
		
		System.out.println(Arrays.equals(result, result2));
		System.out.println(image.getBase64());
		System.out.println(image.getMiniatura());
		System.out.println(fileName);

		System.out.println(tema);
		System.out.println(descricao);

		request.getRequestDispatcher("resumo.jsp").forward(request, response);
	    }
	}

//	    System.out.println(fileContent);

    }

}
