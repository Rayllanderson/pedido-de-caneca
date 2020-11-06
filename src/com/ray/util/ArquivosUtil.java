package com.ray.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import com.ray.model.exceptions.EntradaInvalidaException;

public class ArquivosUtil {

    /**
     * Converte a imagem em base 64, converte em PNG e então inicia o processo de
     * criação de miniatura
     * 
     * @param request
     * @return imagem em forma de miniatura
     * @throws IOException
     * @throws ServletException
     * @throws EntradaInvalidaException tipo de arquivo inválido
     */
    public static String createMiniatureBase64(String base64)
	    throws IOException {

	/* Transforma emum bufferedImage */
	byte[] imageByteDecode = Base64.decodeBase64(base64);
	BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));

	/* Pega o tipo da imagem */
	int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

	/* Cria imagem em miniatura */
	BufferedImage resizedImage = new BufferedImage(200, 200, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(bufferedImage, 0, 0, 200, 200, null);
	g.dispose();

	/* Escrever imagem novamente */
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ImageIO.write(resizedImage, "png", baos);
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
}
