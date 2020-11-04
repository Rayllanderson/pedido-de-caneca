package db.teste.cliente;

import org.junit.Assert;
import org.junit.Test;

import com.ray.model.dao.ClienteRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Cliente;

public class InsertTest {

    private ClienteRepository repository = RepositoryFactory.createClienteDao();
    
    @Test
    public void salvarCliente() {
	
	Cliente cliente = new Cliente(null, "Ray", "99999999");
	
	cliente = repository.save(cliente);
	
	System.out.println(cliente);
	
    }
}
