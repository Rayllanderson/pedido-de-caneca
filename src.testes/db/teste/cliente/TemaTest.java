package db.teste.cliente;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ray.model.dao.ClienteRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.dao.TemaRepository;
import com.ray.model.entities.Tema;

public class TemaTest {

    private TemaRepository repository = RepositoryFactory.createTemaDao();
    
    @Test
    public void salvarCliente() {
	
	Tema cliente = new Tema(null, "Dia das Mães");
	
	cliente = repository.save(cliente);
	
	System.out.println(cliente);
    }

    @Test
    public void updateCliente() {
	
	Tema cliente = new Tema(2L, "Dia dos Pais");
	
	cliente = repository.update(cliente);
	
	System.out.println(cliente);
    }

    @Test
    public void deletarCliente() {
	
	repository.deleteById(3L);
	
    }

    @Test
    public void findCliente() {
	Tema cliente = new Tema(2L, "Dia dos Pais");
	assertThat(repository.findById(2L), is(cliente));
   }
    
    @Test
    public void findAll() {
	List<Tema> clientes = repository.findAll();
	clientes.forEach(System.out::println);
    }
}
