package com.ray.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.dao.TemaRepository;
import com.ray.model.entities.Arquivo;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;
import com.ray.model.entities.Pedido;
import com.ray.model.entities.Tema;
import com.ray.model.entities.enums.Etapa;
import com.ray.model.exceptions.RequisicaoInvalidaException;
import com.ray.model.service.CanecaService;
import com.ray.model.service.ClienteService;
import com.ray.model.service.ImageService;
import com.ray.model.service.PedidoService;
import com.ray.model.validacoes.ClientValidation;
import com.ray.model.validacoes.ImageValidation;
import com.ray.model.validacoes.ThemeValidation;
import com.ray.util.Email;
import com.ray.util.ThreadMiniature;

@MultipartConfig
@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ImageRepository imageRepository;
    private TemaRepository temaRepository;
    private CanecaService canecaService;
    private CanecaRepository canecaRepository;
    private ImageService imageService;
    private PedidoService pedidoService;
    private ClienteService clienteService;

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
	try {
	    String action = request.getParameter("action");
	    if (action != null) {
		if (action.equals("edit")) {
		    setCanecaToEdit(request, response);
		}
	    } else {
		request.getSession().setAttribute("temas", temaRepository.findAll());
		request.getRequestDispatcher("order.jsp").forward(request, response);
	    }
	} catch (Exception e) {
	    response.setStatus(500);
	    e.printStackTrace();
	    response.getWriter().print("Ocorreu um erro.");
	    logout(request);
	}
    }

    private void startRepositories() {
	imageRepository = RepositoryFactory.createImageDao();
	temaRepository = RepositoryFactory.createTemaDao();
	canecaRepository = RepositoryFactory.createCanecaDao();
	canecaService = new CanecaService();
	imageService = new ImageService();
	pedidoService = new PedidoService();
	clienteService = new ClienteService();
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
		} else if (action.equals("finish")) {
		    finish(request, response);
		}
	    }
	} catch (RequisicaoInvalidaException e) {
	    response.setStatus(400);
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    response.setStatus(400);
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().print(e.getMessage());
	}catch (Exception e) {
	    e.printStackTrace();
	    response.getWriter().print("Ocorreu um erro.");
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
		throw new RequisicaoInvalidaException("Caneca não pertence ao usuário");
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
	// carregando a caneca que está sendo editada
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
     * Verifica se o cliente mudou imagem. caso tenha mudado, captura as novas
     * imagens e manda pra processar no método de updateImagens Caso nao tenha
     * alterado, retorna uma lista vazia
     * 
     * @param request
     * @param canecaAntiga
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private List<Arquivo> getImagens(HttpServletRequest request, Caneca canecaAntiga)
	    throws IOException, ServletException {
	final int NUMERO_IMAGENS = 3;
	boolean hasChangedImage = request.getParameter("hasChangedImage").equals("true") ? true : false;
	if (hasChangedImage) {
	    List<Arquivo> novasImagens = new ArrayList<>();
	    for (int i = 1; i <= NUMERO_IMAGENS; i++) {
		novasImagens.add(createImage(request, canecaAntiga, i));
	    }
	    novasImagens.removeAll(Collections.singleton(null));// removendo as imgs nulas
	    return updateImages(novasImagens, canecaAntiga.getId());
	}
	return new ArrayList<Arquivo>();
    }

    /**
     * realiza o update da imagem. <br>
     * Verifica se a nova lista de imagens contém imagem. Depois verifica se tinha
     * imagem anteriormente, caso sim, apaga todas elas e, em seguida, salva as
     * novas. caso a nova lista de imagens nao possua nenhuma imagens, verifica se
     * tinha imagem antes, caso sim, deleta elas e devolve a lista vazia
     * 
     * @param imagensNovas - lista contendo as novas imagens
     * @param canecaId     - id da caneca qe está sendo editada para buscar as
     *                     antigas imagens envolvidas com ela
     * @return caso as novas imagens contenham imagens, retornará elas fazendo todo
     *         procedimento de exclusao;
     */
    private List<Arquivo> updateImages(List<Arquivo> imagensNovas, Long canecaId) {
	List<Arquivo> imagensAntigas = imageService.findAll(canecaId, false);
	imagensAntigas.removeAll(imagensNovas); // removendo as imagens que permaneceram inalteradas e deixando apenas
						// as imagens antigas (que foram removidas)
	boolean hadImageBefore = !imagensAntigas.isEmpty();
	boolean hasImage = !imagensNovas.isEmpty();
	if (hasImage) {
	    if (hadImageBefore) {
		imagensAntigas.forEach(x -> imageService.deleteById(x.getId())); // removendo as imagens anteriores que
										 // foram excluídas
	    }
	    imagensNovas.replaceAll(x -> x.getId() == null ? imageService.save(x) : x); // salvando novas imagens e
											// deixando as antigas como
											// estão
	    return imagensNovas;
	} else if (hadImageBefore) {
	    imagensAntigas.forEach(x -> imageService.deleteById(x.getId()));
	}
	return imagensNovas;
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
     * <br>
     * 
     * @param request - para buscar as informações no lado do cliente
     * @return Uma lista contendo todas as imagens escolhidas do cliente. Retornará
     *         vazia caso o client não escolha nenhuma
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
		imagens.add(new Arquivo(null, fileContent, "", "", part.getContentType(), caneca, part.getSubmittedFileName()));
	    }
	}
	return imagens;
    }

    private Arquivo createImage(HttpServletRequest request, Caneca caneca, int index)
	    throws IOException, ServletException {
	String imageId = request.getParameter("id-" + index);
	boolean hasChangedImage = imageId.equals("null");
	if (hasChangedImage) {
	    Part part = request.getPart("file" + index);
	    if (part.getSize() > 0 && ImageValidation.fileTypeIsValid(request, part)) {
		InputStream fileContent = part.getInputStream();
		return new Arquivo(null, fileContent, "", "", part.getContentType(), caneca);
	    } else {
		return null;
	    }
	} else {
	    return imageRepository.findById(Long.parseLong(imageId));
	}
    }

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

    private void logout(HttpServletRequest request) {
	HttpSession session = request.getSession(false);
	if (session != null) {
	    session.invalidate();
	}
    }

    private void finish(HttpServletRequest request, HttpServletResponse response) throws IOException {
	Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
	java.util.Date currentTime = calendar.getTime();
	Cliente cliente = getCliente(request);
	@SuppressWarnings("unchecked")
	List<Caneca> canecas = (List<Caneca>) request.getSession().getAttribute("canecas");
	if (canecas.isEmpty()) {
	    throw new IllegalArgumentException("Para finalizar, você precisa de pelo menos uma caneca em seu carrinho");
	}
	Pedido p = new Pedido(cliente, currentTime);
	if (pedidoService.save(p)) {
	    request.getSession().setAttribute("order", p);
	    response.setStatus(200);
	    response.sendRedirect("sucess.jsp");
	    new Email(cliente.getNome());
	    logout(request);
	} else {
	    response.setStatus(500);
	    response.sendRedirect("error.jsp");
	}
	return;
    }

    private Cliente getCliente(HttpServletRequest request) {
	try {
	    Cliente c1 = (Cliente) request.getSession().getAttribute("cliente");
	    Long id = Long.valueOf(request.getParameter("id"));
	    Cliente c2 = new Cliente(id, null, null);
	    if (c2.equals(c1)) {
		c2.setNome(request.getParameter("nome"));
		c2.setTelefone(request.getParameter("telefone"));
		return clienteService.update(c2);
	    } else {
		throw new IllegalArgumentException("Ocorreu um erro. Recarregue a página e tente novamente");
	    }
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException("Ocorreu um erro. Recarregue a página e tente novamente");
	}
    }
}
