package com.ray.model.validacoes;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;

public class ClientValidation {

    /**
     * atrav�s do id da caneca, busca a caneca no banco de dados e verifica se o
     * cliente da caneca buscada via id � o mesmo do cliente passado via parametro
     * 
     * @param client
     * @param canecaId
     * @return
     */
    public static boolean clientIsValid(Cliente client, Long canecaId) {
	CanecaRepository repository = RepositoryFactory.createCanecaDao();
	Caneca caneca = repository.findById(canecaId);
	try {
	    return caneca.getCliente().equals(client);
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
    }

    /**
     * Verificando se o id do cliente atual � de fato do cliente que est� realizando o pedido
     * 
     * @param clientId         - id do cliente que est� setado, podendo ser o id verdadeiro ou nao
     * @param servletRequestId - parametro setado quando o cliente faz o login. para
     *                         recuperar, basta dar um getAtribute em 542681256 e
     *                         ir� retornar o id;
     * @return true caso ambos sejam iguais. false caso nao
     */
    public static boolean clientIsValid(Long clientId, Long servletRequestId) {
	return clientId.equals(servletRequestId);
    }

}
