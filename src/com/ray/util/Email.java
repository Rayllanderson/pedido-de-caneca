package com.ray.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email implements Runnable {

    private final String USERNAME = "rayllandersonemailjava@gmail.com";
    private final String PASSWORD = "x";
    private String clientName;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    /**
     * Ao instanciar, manda o email
     *
     */
    public Email(String clientName) {
	this.clientName = clientName;
	Thread t = new Thread(this);
	t.run();
    }

    public void sendEmail() throws ParseException {
	// Recipient's email ID needs to be mentioned.
	String to = "producaolpp100@gmail.com";

//	StringBuilder html = new StringBuilder();

	// properties
	Properties properties = new Properties();

	setGmailProperties(properties);

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
	    message.setSubject("Novo pedido de " + clientName + " às "
		    + sdf.format(new Date()));
//	    html.append(
//		    Html.h4("Você tem um novo pedido. previsão de entrega para o dia " + LocalDate.now().plusDays(3)));

//	    Multipart multipart = new MimeMultipart();
	    // Attachment body part.
//	    MimeBodyPart texto = new MimeBodyPart();

//	    texto.setContent(html.toString(), "text/html; charset=utf-8");

//	    multipart.addBodyPart(texto);
	    
	    message.setText("Você tem um novo pedido. previsão de entrega para o dia " + LocalDate.now().plusDays(3));

	    Transport.send(message);

	} catch (MessagingException mex) {
	    mex.printStackTrace();
	}
    }

    private static void setGmailProperties(Properties properties) {
	String host = "smtp.gmail.com";
	// Setup mail server
	properties.put("mail.smtp.auth", "true");
	properties.put("mail.smtp.starttls", "true");
	properties.put("mail.smtp.host", host);
	properties.put("mail.smtp.port", "465");
	properties.put("mail.smtp.socketFactory.port", "465");
	properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    @Override
    public void run() {
	try {
	    sendEmail();
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
