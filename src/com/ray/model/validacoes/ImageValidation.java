package com.ray.model.validacoes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class ImageValidation {

    /**
     * Verifica se o arquivo � uma imagem. <br>
     * Arquivos de imagens n�o aceitos: GIF
     * @param request
     * @return true caso o tipo do arquivo seja v�lido. False caso n�o seja.
     * @throws IOException
     * @throws ServletException
     */
    public static boolean fileTypeIsValid(HttpServletRequest request) throws IOException, ServletException {
	Part filePart = request.getPart("pictureFile");
	if (filePart.getContentType().contains("image") && !(filePart.getContentType().contains("gif"))) {
	    return true;
	}
	return false;
    }
    
}
