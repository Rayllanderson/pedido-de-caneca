package db.teste.cliente;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Arquivo;
import com.ray.model.entities.Caneca;
import com.ray.model.service.ImageService;

public class ArquivoTeste {

    private ImageRepository repository = RepositoryFactory.createImageDao();
    private ImageService imageService = new ImageService();
    @Test
    public void salvar() {
	Caneca caneca = new Caneca();
	caneca.setId(362L);
	Arquivo image = new Arquivo(null, null, null, "545454", "png", caneca );
	Arquivo image2 = new Arquivo(null, null, null, "545454", "png", caneca );
	List<Arquivo> hm = new ArrayList<>();
	hm.add(image2);
	hm.add(image);
	for (Arquivo arquivo : hm) {
	    System.out.println(imageService.save(arquivo));
	}
    }

    
    @Test
    public void update() {
	
	Arquivo image = new Arquivo(349L, null, null, "33333", "jpg", null);
	System.out.println(repository.update(image, false));
    }

    @Test
    public void deletar() {
	
	repository.deleteById(348L);
	
    }

    @Test
    public void find() {
	System.out.println(repository.findById(340L));
   }
    
    @Test
    public void findAll() {
	List<Arquivo> imagens = repository.findAll(361L);
	imagens.forEach(System.out::println);
    }
}
