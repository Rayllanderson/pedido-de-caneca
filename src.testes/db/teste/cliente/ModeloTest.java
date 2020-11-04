package db.teste.cliente;

import java.util.List;

import org.junit.Test;

import com.ray.model.dao.ModeloRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Modelo;

public class ModeloTest {

    private ModeloRepository repository = RepositoryFactory.createModeloDao();
    
    @Test
    public void salvar() {
	
	Modelo obj = new Modelo(null, "Caneca Padrão");
	
	obj = repository.save(obj);
	
	System.out.println(obj);
    }

    @Test
    public void update() {
	
	Modelo obj = new Modelo(3L, "Caneca de Chopp");
	
	obj = repository.update(obj);
	
	System.out.println(obj);
    }

    @Test
    public void deletar() {
	
	repository.deleteById(1L);
	
    }

    @Test
    public void find() {
	System.out.println(repository.findById(2L));
   }
    
    @Test
    public void findAll() {
	List<Modelo> clientes = repository.findAll();
	clientes.forEach(System.out::println);
    }
}
