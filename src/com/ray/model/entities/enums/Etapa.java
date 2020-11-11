package com.ray.model.entities.enums;

public enum Etapa {

    PEDIDO_REALIZADO(1), ESCOLHA(2), ALTERACAO(3), MODELO_ESCOLHIDO(4), PRODUCAO(5), PRONTO_ENTREGA(6), FINALIZADO(7);

    private int code;

    private Etapa(int code) {
	this.code = code;
    }

    public int getCode() {
	return code;
    }

    public static Etapa valueOf(int code) {
	for (Etapa value : Etapa.values()) {
	    if (value.getCode() == code) {
		return value;
	    }
	}
	throw new IllegalArgumentException("Código de Etapa inválido");
    }
}
