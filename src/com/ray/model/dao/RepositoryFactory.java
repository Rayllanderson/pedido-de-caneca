package com.ray.model.dao;

import com.ray.db.DB;
import com.ray.model.dao.impl.CanecaDaoJdbc;
import com.ray.model.dao.impl.ClienteDaoJdbc;
import com.ray.model.dao.impl.ImageDaoJdbc;
import com.ray.model.dao.impl.ModeloDaoJdbc;
import com.ray.model.dao.impl.TemaDaoJdbc;

public class RepositoryFactory {

    public static ClienteRepository createClienteDao() {
	return new ClienteDaoJdbc(DB.getConnection());
    }

    public static TemaRepository createTemaDao() {
	return new TemaDaoJdbc(DB.getConnection());
    }

    public static ModeloRepository createModeloDao() {
	return new ModeloDaoJdbc(DB.getConnection());
    }

    public static ImageRepository createImageDao() {
	return new ImageDaoJdbc(DB.getConnection());
    }

    public static CanecaRepository createCanecaDao() {
	return new CanecaDaoJdbc(DB.getConnection());
    }
}
