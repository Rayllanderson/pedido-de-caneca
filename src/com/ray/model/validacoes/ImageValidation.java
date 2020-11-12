package com.ray.model.validacoes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class ImageValidation {

    /**
     * Verifica se o arquivo é uma imagem. <br>
     * Arquivos de imagens não aceitos: GIF
     * @param request
     * @return true caso o tipo do arquivo seja válido. False caso não seja.
     * @throws IOException
     * @throws ServletException
     */
    public static boolean fileTypeIsValid(HttpServletRequest request, String requestPartName) throws IOException, ServletException {
	Part filePart = request.getPart(requestPartName);
	if (filePart.getContentType().contains("image") && !(filePart.getContentType().contains("gif"))) {
	    return true;
	}
	return false;
    }
    
}
