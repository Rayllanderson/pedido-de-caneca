package com.ray.model.validacoes;

import com.ray.model.entities.enums.Modelo;

public class ModeloValidation {

    public static Modelo modeloValidation (Modelo modelo) {
	if (modelo == null) {
	    return null;
	}
	return modelo;
    }
}
