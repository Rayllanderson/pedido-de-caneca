package com.ray.model.dao;

import com.ray.db.DB;
import com.ray.model.dao.impl.CanecaDaoJdbc;
import com.ray.model.dao.impl.ClienteDaoJdbc;
import com.ray.model.dao.impl.PedidoDaoJdbc;
import com.ray.model.dao.impl.ArquivoDaoJdbc;
import com.ray.model.dao.impl.TemaDaoJdbc;

public class RepositoryFactory {

    public static ClienteRepository createClienteDao() {
	return new ClienteDaoJdbc(DB.getConnection());
    }

    public static TemaRepository createTemaDao() {
	return new TemaDaoJdbc(DB.getConnection());
    }


    public static ImageRepository createImageDao() {
	return new ArquivoDaoJdbc(DB.getConnection());
    }

    public static CanecaRepository createCanecaDao() {
	return new CanecaDaoJdbc(DB.getConnection());
    }

    public static PedidoRepository createPedidoDao() {
	return new PedidoDaoJdbc(DB.getConnection());
    }
    
}
