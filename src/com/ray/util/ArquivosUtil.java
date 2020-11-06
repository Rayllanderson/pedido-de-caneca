package com.ray.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.imgscalr.Scalr;

import com.ray.model.exceptions.EntradaInvalidaException;

public class ArquivosUtil implements Serializable{


    private static final long serialVersionUID = 1L;

    /**
     * Converte a imagem em base 64, converte em PNG e então inicia o processo de
     * criação de miniatura
     * 
     * @param request
     * @return imagem em forma de miniatura
     * @throws Exception 
     * @throws ServletException
     * @throws EntradaInvalidaException tipo de arquivo inválido
     */
    public static String createMiniatureBase64(String base64)
	    throws Exception {

	/* Transforma emum bufferedImage */
	byte[] imageByteDecode = Base64.decodeBase64(base64);
	BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
	BufferedImage croped = resizeImage(bufferedImage, 320, 320);
	
	/* Pega o tipo da imagem */
	int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

	final int WIDHT = 250;
	final int HEIGHT = 250;
	
	/*
	 /* Cria imagem em miniatura 
	BufferedImage resizedImage = new BufferedImage(WIDHT, HEIGHT, type);
	resizedImage.createGraphics().drawImage(croped, 0, 0, null); */

	/* Escrever imagem novamente */
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ImageIO.write(croped, "png", baos);
	return "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
    }

    /**
     * retorna a base 64 do arquivo
     * 
     * @param 
     * @return "data:" + contentType + ";base64,"
     */
    public static String createBase64(InputStream imagem, String contentType)
	    throws IOException{
	return "data:" + contentType + ";base64," + Base64.encodeBase64String(streamToByte(imagem));
    }
    
    /**
     * 
     * @param imagem
     * @return apenas a base64, sem data e contentType
     * @throws IOException
     */
    public static String createBase64(InputStream imagem) throws IOException{
	return Base64.encodeBase64String(streamToByte(imagem));
    }

    private static byte[] streamToByte(InputStream imagem) throws IOException {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	int reads = imagem.read();
	while (reads != -1) {
	    baos.write(reads);
	    reads = imagem.read();
	}
	return baos.toByteArray();
    }
    
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
	    return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
    }
}
