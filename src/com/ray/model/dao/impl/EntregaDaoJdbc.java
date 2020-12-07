package com.ray.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ray.db.DB;
import com.ray.db.DbException;
import com.ray.model.dao.EntregaRepository;
import com.ray.model.entities.Entrega;

public class EntregaDaoJdbc implements EntregaRepository {

    private Connection conn;

    public EntregaDaoJdbc(Connection conn) {
	this.conn = conn;
    }

    @Override
    public Entrega findById(Long id) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "select * from entregas where id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setLong(1, id);
	    rs = st.executeQuery();
	    if(rs.next()) {
		return new Entrega(rs.getLong("id"), rs.getString("nome"));
	    }else {
		return null;
	    }
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }

    @Override
    public List<Entrega> findAll() {
	PreparedStatement st = null;
	ResultSet rs = null;
	List <Entrega> temas = new ArrayList<>();
	String sql = "select * from entregas";
	try {
	    st = conn.prepareStatement(sql);
	    rs = st.executeQuery();
	    while(rs.next()) {
		temas.add(new Entrega(rs.getLong("id"), rs.getString("nome")));
	    }
	    return temas;
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }

}
