package com.ray.model.dao;

import com.ray.db.DB;
import com.ray.model.dao.impl.ClienteDaoJdbc;

public class RepositoryFactory {

    
    public static ClienteRepository createClienteDao() {
	return new ClienteDaoJdbc(DB.getConnection());
    }
}
