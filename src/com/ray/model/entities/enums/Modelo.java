package com.ray.model.entities.enums;

public enum Modelo {

    PADRAO(1), CHOPP(2);

    private int code;

    private Modelo(int code) {
	this.code = code;
    }

    public int getCode() {
	return code;
    }

    public static Modelo valueOf(int code) {
	for (Modelo value : Modelo.values()) {
	    if (value.getCode() == code) {
		return value;
	    }
	}
	throw new IllegalArgumentException("Código de Modelo inválido");
    }
}
