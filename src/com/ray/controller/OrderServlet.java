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
import com.ray.model.dao.ModeloRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.dao.TemaRepository;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;
import com.ray.model.entities.Image;
import com.ray.model.entities.Modelo;
import com.ray.model.entities.Tema;
import com.ray.model.service.CanecaService;
import com.ray.util.ArquivosUtil;

/**
 * mudar o nome também
 */
@MultipartConfig
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private ImageRepository imageRepository;
    private ModeloRepository modeloRepository;
    private TemaRepository temaRepository;
    private CanecaService canecaService;

    @Override
    public void init() throws ServletException {
	startRepositories();
	super.init();
    }


    public OrderServlet() {
	super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	request.getSession().setAttribute("temas", temaRepository.findAll());
	request.getSession().setAttribute("modelos", modeloRepository.findAll());
	request.getRequestDispatcher("order.jsp").forward(request, response);
    }

    private void startRepositories() {
	imageRepository = RepositoryFactory.createImageDao();
	modeloRepository = RepositoryFactory.createModeloDao();
	temaRepository = RepositoryFactory.createTemaDao();
	canecaService = new CanecaService();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String descricao = request.getParameter("descricao");
	String quantidade = request.getParameter("quantidade");
	Tema tema = temaRepository.findById(Long.valueOf(request.getParameter("tema-id")));
	Modelo modelo =  modeloRepository.findById(Long.parseLong(request.getParameter("modelo-id")));
	
	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
	
	Image image = createImage(request);
	
	if(image != null) {
	  //thread de criar base 64 aqui!
	}
	
	image.setBase64(ArquivosUtil.createBase64(image.getInputStream()));
	try {
	    image.setMiniatura(ArquivosUtil.createMiniatureBase64(image.getBase64()));
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	image = imageRepository.save(image);
	
	Caneca caneca = new Caneca(null, Integer.valueOf(quantidade), tema, modelo, image, cliente, descricao);
	
	//salvar caneca
	canecaService.save(caneca);

	response.sendRedirect("carrinho"); //mandar pra servlet de resumo/carrinho via get
    }

    
    /**
     * Cria uma imagem apenas com o inputstream. Seta base 64 e miniatura pra ""
     * @param request
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private Image createImage(HttpServletRequest request) throws IOException, ServletException {
	Part filePart = request.getPart("pictureFile"); // Retrieves <input type="file" name="pictureFile">
	InputStream fileContent = filePart.getInputStream();
	String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	//criar validacoes em breve
	return new Image(null, fileContent, "", "");
    }


//	    System.out.println(fileContent);

}
