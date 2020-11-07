package com.ray.model.validacoes;

import com.ray.model.entities.Tema;
import com.ray.model.exceptions.RequisicaoInvalidaException;

public class ThemeValidation {
    
    /**
     * Verifica se o tema selecionado é válido. Caso seja null, throws exception
     * @param tema
     * @throws RequisicaoInvalidaException
     */
    public static void validateTheme(Tema tema) throws RequisicaoInvalidaException {
	if (tema == null) {
	    throw new RequisicaoInvalidaException("Você deve escolher um tema da lista");
	}
    }

}
