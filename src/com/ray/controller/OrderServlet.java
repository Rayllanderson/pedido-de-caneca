package com.ray.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
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
import com.ray.model.entities.Arquivo;
import com.ray.model.entities.Tema;
import com.ray.model.entities.enums.Etapa;
import com.ray.model.exceptions.RequisicaoInvalidaException;
import com.ray.model.service.CanecaService;
import com.ray.model.service.ImageService;
import com.ray.model.validacoes.ClientValidation;
import com.ray.model.validacoes.ImageValidation;
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
	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");

	String id = request.getParameter("id");
	boolean hasId = id != null;
	if (hasId) {
	    System.out.println(id);
	    if (ClientValidation.clientIsValid(cliente, Long.valueOf(id))) {
		update(request, descricao, quantidade, tema, cliente, id);
	    } else {
		throw new RequisicaoInvalidaException("Caneca n�o pertence ao usu�rio");
	    }
	} else {
	    save(request, descricao, quantidade, tema, cliente);
	    response.setStatus(201);
	}
	response.sendRedirect("carrinho");
    }

    private void save(HttpServletRequest request, String descricao, String quantidade, Tema tema, Cliente cliente)
	    throws IOException, ServletException {
	Caneca caneca = new Caneca(null, Integer.valueOf(quantidade), tema, Etapa.PEDIDO_REALIZADO, cliente, descricao);
	caneca = canecaService.save(caneca);
	List<Arquivo> imagens = createImages(request, caneca);
	boolean hasImage = !imagens.isEmpty();
	if (hasImage) {
	    imagens.replaceAll(x -> x = imageService.save(x));
	    imagens.forEach(x -> new ThreadMiniature(x));
	}
    }

    private void update(HttpServletRequest request, String descricao, String quantidade, Tema tema, Cliente cliente,
	    String id) throws IOException, ServletException {
	List<Arquivo> imagens = new ArrayList<>();
	Caneca caneca = new Caneca(Long.valueOf(id), Integer.valueOf(quantidade), tema, Etapa.PEDIDO_REALIZADO, cliente,
		descricao);
	// carregando a caneca que est� sendo editada
	Caneca c = (Caneca) request.getSession().getAttribute("caneca");
	// atualiza a imagem caso o user tenha mudado
	imagens = getImagens(request, c);
	boolean hasImage = !imagens.isEmpty();
	if (hasImage) {
	    imagens.forEach(x -> new ThreadMiniature(x));
	}
	canecaService.update(caneca);
	request.getSession().setAttribute("caneca", "");
    }

    /**
     * Verifica se o cliente mudou imagem. caso tenha mudado, captura as novas imagens e manda pra processar no m�todo de updateImagens
     * Caso nao tenha alterado, retorna uma lista vazia
     * @param request
     * @param canecaAntiga
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private List<Arquivo> getImagens(HttpServletRequest request, Caneca canecaAntiga)
	    throws IOException, ServletException {
	boolean hasChangedImage = request.getParameter("hasChangedImage").equals("true") ? true : false;
	if (hasChangedImage) {
	    List<Arquivo> novasImagens = createImages(request, canecaAntiga);
	    return updateImages(novasImagens, canecaAntiga.getId());
	}
	return new ArrayList<Arquivo>();
    }

    /**
     * realiza o update da imagem. <br>
     * Verifica se a nova lista de imagens cont�m imagem. Depois verifica se tinha
     * imagem anteriormente, caso sim, apaga todas elas e, em seguida, salva as
     * novas. caso a nova lista de imagens nao possua nenhuma imagens, verifica se tinha imagem antes, caso
     * sim, deleta elas e devolve a lista vazia
     * 
     * @param imagensNovas - lista contendo as novas imagens
     * @param canecaId - id da caneca qe est� sendo editada para buscar as antigas imagens envolvidas com ela
     * @return caso as novas imagens contenham imagens, retornar� elas fazendo todo procedimento de exclusao;
     */
    private List<Arquivo> updateImages(List<Arquivo> imagensNovas, Long canecaId) {
	List<Arquivo> imagensAntigas = imageService.findAll(canecaId, false);
	boolean hadImageBefore = !imagensAntigas.isEmpty();
	boolean hasImage = !imagensNovas.isEmpty();
	if (hasImage) {
	    if (hadImageBefore) {
		imagensAntigas.forEach(x -> imageService.deleteById(x.getId())); // deletando todas as imagens
	    }
	    imagensNovas.replaceAll(x -> imageService.save(x)); // salvando novas imagens
	    return imagensNovas;
	} else if (hadImageBefore) {
	    imagensAntigas.forEach(x -> imageService.deleteById(x.getId()));
	}
	return imagensNovas;
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
     * <br>
     * 
     * @param request - para buscar as informa��es no lado do cliente
     * @return Uma lista contendo todas as imagens escolhidas do cliente. Retornar�
     *         vazia caso o client n�o escolha nenhuma
     * @throws IOException
     * @throws ServletException
     */
    private List<Arquivo> createImages(HttpServletRequest request, Caneca caneca) throws IOException, ServletException {
	List<Part> parts = new ArrayList<>();
	List<Arquivo> imagens = new ArrayList<>();
	parts.add(request.getPart("file1"));
	parts.add(request.getPart("file2"));
	parts.add(request.getPart("file3"));
	for (Part part : parts) {
	    if (part.getSize() > 0 && ImageValidation.fileTypeIsValid(request, part)) {
		InputStream fileContent = part.getInputStream();
		imagens.add(new Arquivo(null, fileContent, "", "", part.getContentType(), caneca));
	    }
	}
	return imagens;
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
		caneca.getFotos().addAll(imageService.findAll(canecaId, false));
		request.getSession().setAttribute("temas", temaRepository.findAll());
		request.getSession().setAttribute("caneca", caneca);
		response.setStatus(200);
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
