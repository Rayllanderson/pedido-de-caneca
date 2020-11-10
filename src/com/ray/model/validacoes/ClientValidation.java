package com.ray.model.validacoes;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;

public class ClientValidation {
    
    /**
     * através do id da caneca, busca a caneca no banco de dados e verifica se o cliente da caneca buscada via id é o mesmo do cliente passado via parametro
     * @param client
     * @param canecaId
     * @return
     */
    public static boolean clientIsValid(Cliente client, Long canecaId) {
	CanecaRepository repository = RepositoryFactory.createCanecaDao();
	Caneca caneca = repository.findById(canecaId);
	try{
	    return caneca.getCliente().equals(client);
	}catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
    }

}
