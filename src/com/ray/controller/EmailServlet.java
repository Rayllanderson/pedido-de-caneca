package com.ray.controller;

//File Name SendEmail.java
import java.io.IOException;
import java.util.Date;
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
    private final String PASSWORD = "*******";
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
	Caneca caneca  = (Caneca) request.getSession().getAttribute("caneca");
	caneca = repository.findById(caneca.getId());

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

	response.setContentType("text/plain");
	response.setCharacterEncoding("UTF-8");
	try {
	    // Create a default MimeMessage object.
	    MimeMessage message = new MimeMessage(session);

	    // Set From: header field of the header.
	    message.setFrom(new InternetAddress(USERNAME));

	    // Set To: header field of the header.
	    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	    // Set Subject: header field - titulo email
	    message.setSubject("Pedido de caneca - Novo email de " + cliente.getNome() + new Date());

	    // Now set the actual message
//	    message.setText("teste");

	    // Send message
	 //   Transport.send(message);

	    //new
	    
	   // message.setSubject("Subject Line");

	    Multipart emailContent = new MimeMultipart();

	    // Attachment body part.
	    MimeBodyPart att = new MimeBodyPart(); 
	    String contentType = caneca.getImage().getContentType();
	    System.out.println(contentType);
	    ByteArrayDataSource ds = new ByteArrayDataSource(caneca.getImage().getInputStream(), contentType);
	    att.setDataHandler(new DataHandler(ds));
	    att.setFileName(cliente.getNome() + "." +contentType.split("/")[1]);
	    att.setHeader("Content-Type", contentType);
	    // Attach body parts
	    emailContent.addBodyPart(att);

	    // Attach multipart to message
	    message.setContent(emailContent);

	    Transport.send(message);
	    System.out.println("Sent message");

	    response.getWriter().write("email enviado com sucesso!");
	    response.setStatus(200);
	} catch (MessagingException mex) {
	    mex.printStackTrace();
	    response.getWriter().write("Ocorreu um erro");
	    response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
	}
    }
}
