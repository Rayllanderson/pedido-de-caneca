package com.ray.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Arquivo;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;
import com.ray.model.service.CanecaService;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CanecaRepository canecaRepository;
    private CanecaService canecaService;
    private ImageRepository imgRepository;

    @Override
    public void init() throws ServletException {
	canecaRepository = RepositoryFactory.createCanecaDao();
	canecaService = new CanecaService();
	imgRepository = RepositoryFactory.createImageDao();
	super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
	String action = request.getParameter("action");
	List<Caneca> canecas = canecaRepository.findAll(cliente.getId());
	canecas.forEach(x -> x.getFotos().add(getFirstImage(x.getId()))); 
//	canecas.stream().filter(x -> x.getFotos().isEmpty()).forEach(x -> x.getFotos().add(imgRepository.findById(0L))); //caneca sem foto
	setQuantidadeCanecas(request, canecas);
	if (action != null) {
	    if (action.equals("load-miniature") && thumbIsLoading(canecas)) {
		canecas.forEach(x -> loadThumb(x.getFotos(), false));
		response.setStatus(200);
		return;
	    } else if (action.equals("delete")) {
		delete(request, response, cliente.getId());
	    }
	} else {
	    request.getSession().setAttribute("canecas", canecas);
	    request.getRequestDispatcher("carrinho.jsp").forward(request, response);
	}
    }

    private Arquivo getFirstImage(Long canecaId) {
	List<Arquivo> imagens = imgRepository.findAll(canecaId);
	Optional<Arquivo> firstImage = imagens.stream().findFirst();
	if (firstImage.isPresent()) {
	    return firstImage.get();
	}else
	    return imgRepository.findById(0L); // caneca sem foto
    }

    /**
     * Se alguma thumb estiver com valor vazio (ou seja, carregando), return true;
     * 
     * @param canecas
     * @return
     */
    private boolean thumbIsLoading(List<Caneca> canecas) {
	for (Caneca caneca : canecas) {
	    for (Arquivo i : caneca.getFotos()) {
		if (i.getMiniatura().equals("")) {
		    return true;
		}
	    }
	}

	return false;
    }

    /**
     * Enquanto a thread que cria miniatura não termina, ela irá buscar ela mesma no
     * banco de dados pra verificar se a criação da miniatura já terminou.
     * 
     * @param loadAll - setar para true caso queira carregar todas as miniaturas. False para carregar apenas a primeira
     * @param imagens - lista de imagens de uma caneca
     */
    private void loadThumb(List<Arquivo> imagens, boolean loadAll){
	if(loadAll) {
	    for (Arquivo i : imagens) {
		    while (i.getMiniatura().equals("")) {
			i = imgRepository.findById(i.getId());
		    }
		}
	}else {
	    Arquivo firstImage = imagens.get(0);
	    while (firstImage.getMiniatura().equals("")) {
		firstImage = imgRepository.findById(firstImage.getId());
	    }
	}
    }

    private void delete(HttpServletRequest request, HttpServletResponse response, Long clienteId) {
	Long id = Long.valueOf(request.getParameter("id1"));
	if (canecaService.deleteById(id, clienteId)) {
//	    List<Caneca> canecas = canecaRepository.findAll(clienteId);
//	    request.getSession().setAttribute("canecas", canecas);
//	    request.getSession().setAttribute("size", canecas.size());
	    response.setStatus(204);
	} else {
	    response.setStatus(404);
	}
    }

    private void setQuantidadeCanecas(HttpServletRequest request, List<Caneca> canecas) {
	request.getSession().setAttribute("size", canecas.size());
	if (canecas.size() == 1) {
	    request.getSession().setAttribute("txt", "Caneca");
	} else {
	    request.getSession().setAttribute("txt", "Canecas");
	}
    }
}
