package com.ray.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.dao.TemaRepository;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;
import com.ray.model.entities.Image;
import com.ray.model.entities.Tema;
import com.ray.model.entities.enums.Modelo;
import com.ray.model.exceptions.RequisicaoInvalidaException;
import com.ray.model.service.CanecaService;
import com.ray.model.validacoes.ModeloValidation;
import com.ray.model.validacoes.ThemeValidation;
import com.ray.util.ThreadMiniature;

/**
 * mudar o nome tamb�m
 */
@MultipartConfig
@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ImageRepository imageRepository;
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
	request.getRequestDispatcher("order.jsp").forward(request, response);
    }

    private void startRepositories() {
	imageRepository = RepositoryFactory.createImageDao();
	temaRepository = RepositoryFactory.createTemaDao();
	canecaService = new CanecaService();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	try {
	    String action = request.getParameter("action");
	    if (action != null) {
		if (action.equals("check-file-type")) {
		    checkFileType(request, response);
		} else if (action.equals("next")) {
		    salvarCaneca(request, response);
		}
	    }
	} catch (RequisicaoInvalidaException e) {
	    // TODO: setar response com erro
	}
    }

    private void salvarCaneca(HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException {
	String descricao = request.getParameter("descricao");
	String quantidade = request.getParameter("quantidade");

	Tema tema = temaRepository.findById(Long.valueOf(request.getParameter("tema-id")));
	ThemeValidation.validateTheme(tema);

	Modelo modelo = ModeloValidation.getModeloByNumber(Integer.parseInt(request.getParameter("modelo-id")));

	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");

	Image image = createImage(request);
	Thread t = null;
	if (image.getId() == null) { // usuario escolheu uma foto
	    image = imageRepository.save(image);
	    t = new Thread(new ThreadMiniature(image));
	}

	Caneca caneca = new Caneca(null, Integer.valueOf(quantidade), tema, modelo, image, cliente, descricao);
	canecaService.save(caneca);

	response.sendRedirect("carrinho");
	
	if(t != null) {
	    t.start();
	}
    }

    /**
     * vaerifica se o tipo do arquivo � v�lido. se for, set status (200 OK) sen�o,
     * seta (400 BAD REQUEST)
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void checkFileType(HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException {
	if (fileTypeIsValid(request)) {
	    response.setStatus(HttpServletResponse.SC_OK);
	    return;
	} else {
	    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    return;
	}
    }

    /**
     * Verifica se o arquivo � uma imagem. <br>
     * Arquivos de imagens n�o aceitos: GIF
     * @param request
     * @return true caso o tipo do arquivo seja v�lido. False caso n�o seja.
     * @throws IOException
     * @throws ServletException
     */
    private boolean fileTypeIsValid(HttpServletRequest request) throws IOException, ServletException {
	Part filePart = request.getPart("pictureFile");
	if (filePart.getContentType().contains("image") && !(filePart.getContentType().contains("gif"))) {
	    return true;
	}
	return false;
    }

    /**
     * Cria uma imagem apenas com o inputstream. Seta base 64 e miniatura pra ""
     * Caso o arquivo seja inv�lido ou n�o tenha imagem, retorn imagem com ID 0, que
     * � a imagem de caneca sem foto
     * 
     * @param request
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private Image createImage(HttpServletRequest request) throws IOException, ServletException {
	Part filePart = request.getPart("pictureFile"); // Retrieves <input type="file" name="pictureFile">
	if (fileTypeIsValid(request)) {
	    InputStream fileContent = filePart.getInputStream();
//	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    return new Image(null, fileContent, "", "", filePart.getContentType());
	} else {
	    return new Image(0L, null, null, null, null); // caneca sem foto
	}

    }
}
