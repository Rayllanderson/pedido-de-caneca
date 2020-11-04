package db.teste.cliente;

import java.util.List;

import org.junit.Test;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Image;
import com.ray.model.entities.Modelo;
import com.ray.model.entities.Tema;

public class CanecaTest {

    private CanecaRepository repository = RepositoryFactory.createCanecaDao();
    
    @Test
    public void salvar() {
	
	Caneca obj = new Caneca(null, 1, new Tema(2L, null ), new Modelo(2L, null), new Image(1L, null, null, null));
	
	obj = repository.save(obj);
	
	System.out.println(obj);
    }

    @Test
    public void update() {
	
	Caneca obj = new Caneca(2L, 2, new Tema(1L, null ), new Modelo(3L, null), new Image(1L, null, null, null));
	
	obj = repository.update(obj);
	
	System.out.println(obj);
    }

    @Test
    public void deletar() {
	
	repository.deleteById(3L);
	
    }

    @Test
    public void find() {
	System.out.println(repository.findById(2L));
   }
    
    @Test
    public void findAll() {
	List<Caneca> clientes = repository.findAll();
	clientes.forEach(System.out::println);
    }
}
