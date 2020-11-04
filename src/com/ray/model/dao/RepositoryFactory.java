package com.ray.model.dao;

import com.ray.db.DB;
import com.ray.model.dao.impl.ClienteDaoJdbc;
import com.ray.model.dao.impl.TemaDaoJdbc;

public class RepositoryFactory {

    
    public static ClienteRepository createClienteDao() {
	return new ClienteDaoJdbc(DB.getConnection());
    }
    
    public static TemaRepository createTemaDao() {
   	return new TemaDaoJdbc(DB.getConnection());
       }
}
