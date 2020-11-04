package db.teste.cliente;

import java.util.List;

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

//    @Test
//    public void updateCliente() {
//	
//	Cliente cliente = new Cliente(2L, "Rayllanderson", "88888888");
//	
//	cliente = repository.update(cliente);
//	
//	System.out.println(cliente);
//    }

//    @Test
//    public void deletarCliente() {
//	
//	repository.deleteById(4L);
//	
//    }

//    @Test
//    public void findCliente() {
//	Cliente cliente = new Cliente(2L, "Rayllanderson", "88888888");
//	assertThat(repository.findById(2L), is(cliente));
//    }
    
    @Test
    public void findCliente() {
	List<Cliente> clientes = repository.findAll();
	clientes.forEach(System.out::println);
    }
}
