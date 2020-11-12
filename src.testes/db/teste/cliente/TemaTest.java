package db.teste.cliente;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Test;

import com.ray.model.dao.RepositoryFactory;
import com.ray.model.dao.TemaRepository;
import com.ray.model.entities.Tema;

public class TemaTest {

    private TemaRepository repository = RepositoryFactory.createTemaDao();

    @Test
    public void find() {
	Tema cliente = new Tema(2L, "Dia dos Pais");
	assertThat(repository.findById(2L), is(cliente));
   }
    
    @Test
    public void findAll() {
	List<Tema> clientes = repository.findAll();
	clientes.forEach(System.out::println);
    }
}
