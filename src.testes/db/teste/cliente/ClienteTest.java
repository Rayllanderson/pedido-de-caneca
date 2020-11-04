package db.teste.cliente;

import org.junit.Assert;
import org.junit.Test;

import com.ray.model.dao.ClienteRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Cliente;

public class ClienteTest {

    private ClienteRepository repository = RepositoryFactory.createClienteDao();
//    
//    @Test
//    public void salvarCliente() {
//	
//	Cliente cliente = new Cliente(null, "Ray", "99999999");
//	
//	cliente = repository.save(cliente);
//	
//	System.out.println(cliente);
//    }
    
    @Test
    public void updateCliente() {
	
	Cliente cliente = new Cliente(2L, "Rayllanderson", "88888888");
	
	cliente = repository.update(cliente);
	
	System.out.println(cliente);
    }
}
