package com.ray.model.validacoes;

import com.ray.model.entities.enums.Modelo;

public class ModeloValidation {

    /**
     * verifica se o codigo passado pertence a um modelo da lista. 
     * @param modeloValue
     * @return retorna se encontrar, senao retorna modelo padrao.
     */
    public static Modelo getModeloByNumber (int modeloValue) {
	try{
	    return Modelo.valueOf(modeloValue);
	}catch (IllegalArgumentException e) {
	    return Modelo.PADRAO;
	}
	
    }
}
