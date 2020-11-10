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

import com.ray.model.dao.CanecaRepository;
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
import com.ray.model.service.ImageService;
import com.ray.model.validacoes.ClientValidation;
import com.ray.model.validacoes.ImageValidation;
import com.ray.model.validacoes.ModeloValidation;
import com.ray.model.validacoes.ThemeValidation;
import com.ray.util.ThreadMiniature;

/**
 * mudar o nome também
 */
@MultipartConfig
@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ImageRepository imageRepository;
    private TemaRepository temaRepository;
    private CanecaService canecaService;
    private CanecaRepository canecaRepository;
    private ImageService imageService;

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
	String action = request.getParameter("action");
	if (action != null) {
	    if (action.equals("edit")) {
		setCanecaToEdit(request, response);
	    }
	} else {
	    request.getSession().setAttribute("temas", temaRepository.findAll());
	    request.getRequestDispatcher("order.jsp").forward(request, response);
	}
    }

    private void startRepositories() {
	imageRepository = RepositoryFactory.createImageDao();
	temaRepository = RepositoryFactory.createTemaDao();
	canecaRepository = RepositoryFactory.createCanecaDao();
	canecaService = new CanecaService();
	imageService = new ImageService();
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
		    saveOrUpdate(request, response);
		}
	    }
	} catch (RequisicaoInvalidaException e) {
	    // TODO: setar response com erro
	}
    }

    private void saveOrUpdate(HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException, RequisicaoInvalidaException {
	String descricao = request.getParameter("descricao");
	String quantidade = request.getParameter("quantidade");

	Tema tema = temaRepository.findById(Long.valueOf(request.getParameter("tema-id")));
	ThemeValidation.validateTheme(tema);

	Modelo modelo = ModeloValidation.getModeloByNumber(Integer.parseInt(request.getParameter("modelo-id")));

	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");

	String id = request.getParameter("id");
	boolean hasId = id != null;
	Thread t = null;
	if (hasId) {
	    System.out.println(id);
	    if (ClientValidation.clientIsValid(cliente, Long.valueOf(id))) {
		t = update(request, descricao, quantidade, tema, modelo, cliente, id, t);
	    }else {
		throw new RequisicaoInvalidaException("Caneca não pertence ao usuário");
	    }
	} else {
	    t = save(request, descricao, quantidade, tema, modelo, cliente, t);
	}
	response.sendRedirect("carrinho");
	if (t != null) {
	    t.start();
	}
    }

    private Thread save(HttpServletRequest request, String descricao, String quantidade, Tema tema, Modelo modelo,
	    Cliente cliente, Thread t) throws IOException, ServletException {
	Image image;
	image = createImage(request);
	if (image.getId() == null) { // usuario escolheu uma foto
	    image = imageRepository.save(image);
	    t = new Thread(new ThreadMiniature(image));
	}
	Caneca caneca = new Caneca(null, Integer.valueOf(quantidade), tema, modelo, image, cliente, descricao);
	canecaService.save(caneca);
	return t;
    }

    private Thread update(HttpServletRequest request, String descricao, String quantidade, Tema tema, Modelo modelo,
	    Cliente cliente, String id, Thread t) throws IOException, ServletException {
	Image image;
	Caneca caneca = new Caneca(Long.valueOf(id), Integer.valueOf(quantidade), tema, modelo, null, cliente,
		descricao);
	// carregando a caneca que está sendo editada
	Caneca c = (Caneca) request.getSession().getAttribute("caneca");
	// atualiza a imagem caso o user tenha mudado
	image = updateImage(request, c);
	t = new Thread(new ThreadMiniature(image));
	caneca.setImage(image);
	canecaService.update(caneca);
//	request.getSession().setAttribute("caneca", "");
	return t;
    }

    /**
     * realiza o update da imagem. <br>
     * <strong> Caso 1: o cliente mudou de imagem: </strong>este método irá realizar
     * um update na imagem, substituindo a imagem antiga; <br>
     * <strong> Caso 2: o cliente removeu a imagem: </strong>Caso o cliente remova a
     * imagem, ele retornará uma imagem com ID 0 (sem imagem) e excluirá do banco a
     * antiga imagem; <br>
     * <strong> Case 3: o cliente não possuía imagem, mas colocou uma nova imagem
     * agora: </strong>Nesse caso, este método criará uma nova imagem, salvando uma
     * nova imagem no banco de dados; <br>
     * <strong> Caso 4: o cliente não alterou nada na imagem: </strong> Este método
     * apenas retornará a imagem que está salva no banco de dados via id;
     * 
     * @param request
     * @param c       - antiga caneca a ser editada
     * @return caso a imagem seja alterada, retornará ela, senao retornará a imagem
     *         que estava antes;
     * @throws IOException
     * @throws ServletException
     */
    private Image updateImage(HttpServletRequest request, Caneca c) throws IOException, ServletException {
	boolean hasChangedImage = request.getParameter("hasChangedImage").equals("true") ? true : false;
	boolean hadImageBefore = !(c.getImage().getId() == 0);
	boolean hadNoImageBefore = (c.getImage().getId() == 0);
	boolean updateInputStream = true;
	if (hasChangedImage) {
	    Image image = createImage(request);
	    boolean hasImage = image.getId() == null;
	    if (hasImage) {
		if (hadImageBefore) {
		    image.setId(c.getImage().getId());
		    return imageService.update(image, updateInputStream);
		} else if (hadNoImageBefore) {
		    return imageService.save(image);
		}
	    } else if (hadImageBefore) {
		Long oldImageId = c.getImage().getId();
		c.setImage(image); // desvinculando a imagem da caneca para ser deletada
		canecaService.update(c);
		imageService.deleteById(oldImageId);
	    }
	    return image;
	} else {
	    return imageRepository.findById(c.getImage().getId());
	}
    }

    /**
     * vaerifica se o tipo do arquivo é válido. se for, set status (200 OK) senão,
     * seta (400 BAD REQUEST)
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void checkFileType(HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException {
	if (ImageValidation.fileTypeIsValid(request)) {
	    response.setStatus(HttpServletResponse.SC_OK);
	    return;
	} else {
	    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    return;
	}
    }

    /**
     * Cria uma imagem apenas com o inputstream. Seta base 64 e miniatura pra ""
     * (Vazio) <br>
     * Caso o arquivo seja inválido ou não tenha imagem, retorna imagem com ID 0
     * <br>
     * Imagem com ID 0 significa que a caneca não possui nenhuma imagem, setará a
     * caneca como "Caneca sem foto"
     * 
     * @param request
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private Image createImage(HttpServletRequest request) throws IOException, ServletException {
	Part filePart = request.getPart("pictureFile"); // Retrieves <input type="file" name="pictureFile">
	if (ImageValidation.fileTypeIsValid(request)) {
	    InputStream fileContent = filePart.getInputStream();
//	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    return new Image(null, fileContent, "", "", filePart.getContentType());
	} else {
	    return new Image(0L, null, null, null, null); // caneca sem foto
	}
    }

    /**
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void setCanecaToEdit(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	try {
	    Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
	    Long canecaId = Long.valueOf(request.getParameter("id"));
	    if (ClientValidation.clientIsValid(cliente, canecaId)) {
		Caneca caneca = canecaRepository.findByIdWihoutIS(canecaId);
		request.getSession().setAttribute("caneca", caneca);
		response.setStatus(200);
		request.getSession().setAttribute("temas", temaRepository.findAll());
		return;
	    } else {
		response.setStatus(400);
		return;
	    }
	} catch (NullPointerException e) {
	    response.setStatus(400);
	    return;
	}
    }
}
