package com.ray.controller;

//File Name SendEmail.java
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;

@WebServlet("/email1")
public class EmailServlet extends HttpServlet {

    private final String USERNAME = "rayllandersonemailjava@gmail.com";
    private final String PASSWORD = "*";
    private CanecaRepository repository;

    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
	repository = RepositoryFactory.createCanecaDao();
	super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	// Recipient's email ID needs to be mentioned.
	String to = "rayllanderson@gmail.com";

	// Assuming you are sending email from localhost
	String host = "smtp.gmail.com";

	// cliente
	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
	List<Caneca> canecas = (List<Caneca>) repository.findAll(cliente.getId());

	StringBuilder html = new StringBuilder();

	// properties
	Properties properties = new Properties();

	// Setup mail server
	properties.put("mail.smtp.auth", "true");
	properties.put("mail.smtp.starttls", "true");
	properties.put("mail.smtp.host", host);
	properties.put("mail.smtp.port", "465");
	properties.put("mail.smtp.socketFactory.port", "465");
	properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

	// Get the default Session object.
	Session session = Session.getDefaultInstance(properties, new Authenticator() {
	    @Override
	    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(USERNAME, PASSWORD);
	    }
	});

	try {
	    // Create a default MimeMessage object.
	    MimeMessage message = new MimeMessage(session);

	    // Set From: header field of the header.
	    message.setFrom(new InternetAddress(USERNAME));

	    // Set To: header field of the header.
	    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	    // Set Subject: header field - titulo email
	    message.setSubject("Novo pedido de " + cliente.getNome() + " às " + new Date());

	    //colocando os dados do cliente no corpo
	    html.append(dadosCliente(cliente));
	   
	    Multipart multipart = new MimeMultipart();

	    // Attachment body part.
	    MimeBodyPart texto = new MimeBodyPart();

	    int index = 1;
	    for (Caneca c : canecas) {
		boolean hasImage = c.getImage().getId() != 0;
		MimeBodyPart anexo = new MimeBodyPart();
		String contentType = c.getImage().getContentType();
		InputStream inputStream = c.getImage().getInputStream();
		html.append(dadosCanecas(index, c)); //dados caneca no corpo
		if (hasImage) {
		    ByteArrayDataSource ds = new ByteArrayDataSource(inputStream, contentType);
		    anexo.setDataHandler(new DataHandler(ds));
		    anexo.setFileName("Caneca " + index + " cliente " + cliente.getNome() + "." + contentType.split("/")[1]);
		    // Attach body parts
		    multipart.addBodyPart(anexo);
		}
		index++;
	    }
	    multipart.addBodyPart(texto);

	    texto.setContent(html.toString(), "text/html; charset=utf-8");
	    // Attach multipart to message
	    message.setContent(multipart);

	    Transport.send(message);
	    System.out.println("Sent message");

	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write("email enviado com sucesso!");
	    response.setStatus(200);
	} catch (MessagingException mex) {
	    mex.printStackTrace();
	    response.getWriter().write("Ocorreu um erro");
	    response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
	}
    }

    private String dadosCanecas(int index, Caneca c) {
	final String START_TAG = "<h4>";
	final String END_TAG = "</h4>";
	
	StringBuilder html = new StringBuilder();
	html.append("<h1> Dados da(s) Caneca(s) </h1>");
	String fotoPersonalizada = (c.getImage().getId() != 0) ? "Sim" : "Não";
//	String nomeArquivo = hasImage ? "Caneca " + index + "." + contentType.split("/")[1] : "";
	html.append("<h2>Caneca " + index + "</h2>");
	html.append(START_TAG + "Tema: " + c.getTema() + END_TAG);
	html.append(START_TAG + "Quantidade: " + c.getQuantidade() + END_TAG);
	html.append(START_TAG + "Foto personalizada? " + fotoPersonalizada + END_TAG);
	html.append(START_TAG + "Tipo: " + c.getModelo() + END_TAG);
	html.append("<hr>");
	return html.toString();
    }

    private String dadosCliente(Cliente cliente) {
	final String START_TAG = "<h4>";
	final String END_TAG = "</h4>";
	
	String telefone = cliente.getTelefone();
	telefone = telefone.replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
	String nome = cliente.getNome().split(" ")[0];
	String href = "https://wa.me/55" + telefone + "?text=Olá,%20" + nome.substring(0, 1).toUpperCase().concat(nome.substring(1))
		+ "!%20Recebemos%20seu%20pedido%20:)";
	
	StringBuilder html = new StringBuilder();
	html.append("<h1> Dados do Cliente </h1>");
	html.append(START_TAG + "Nome: " + cliente.getNome() + END_TAG);
	html.append(START_TAG + "Telefone: " + cliente.getTelefone() + END_TAG);
	html.append("<a href=\"" + href + "\">Clique aqui pra mandar mensagem para o cliente</a>");
	html.append("<hr>");
	return html.toString();
    }
}
