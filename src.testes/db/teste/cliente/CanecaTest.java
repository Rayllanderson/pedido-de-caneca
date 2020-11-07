package db.teste.cliente;

import java.util.List;

import org.junit.Test;

import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;
import com.ray.model.entities.Image;
import com.ray.model.entities.Tema;
import com.ray.model.entities.enums.Modelo;

public class CanecaTest {

    private CanecaRepository repository = RepositoryFactory.createCanecaDao();
    
    @Test
    public void salvar() {
	
	Caneca obj = new Caneca(null, 1, new Tema(1L, null ), Modelo.PADRAO, new Image(0L, null, null, null, null), new Cliente(22L, null, null), "Imagem com tuututu tatata");
	
	obj = repository.save(obj);
	
	System.out.println(obj);
    }


    @Test
    public void update() {
	
	Caneca obj = new Caneca(117L, 2, new Tema(1L, null ), Modelo.CHOPP, new Image(0L, null, null, null, null), new Cliente(22L, null, null), "");
	
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
	List<Caneca> clientes = repository.findAll(2L);
	clientes.forEach(System.out::println);
    }
}
