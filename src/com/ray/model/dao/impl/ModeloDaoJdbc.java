package com.ray.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ray.db.DB;
import com.ray.db.DbException;
import com.ray.model.dao.ModeloRepository;
import com.ray.model.entities.Modelo;

public class ModeloDaoJdbc implements ModeloRepository {

    private Connection conn;
    private String tableName = "modelos";
    
    public ModeloDaoJdbc(Connection conn) {
	this.conn = conn;
    }

    /**
     * Retorna o cliente salvo do banco do de dados
     */
    @Override
    public Modelo save(Modelo modelo) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "insert into " + tableName + " (nome) values (?)";
	try {
	    st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    st.setString(1, modelo.getNome());
	    if (st.executeUpdate() > 0) {
		rs = st.getGeneratedKeys();
		if (rs.next()) {
		    return findById(rs.getLong(1));
		}
	    }
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	    DB.closeResultSet(rs);
	}
	return null;
    }

    @Override
    public Modelo update(Modelo modelo) {
	PreparedStatement st = null;
	String sql = "update " + tableName + " set nome = ? where id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setString(1, modelo.getNome());
	    st.setLong(2, modelo.getId());
	    st.executeUpdate();
	    return this.findById(modelo.getId());
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }

    @Override
    public void deleteById(Long id) {
	PreparedStatement st = null;
	String sql = "delete from " + tableName + " where id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setLong(1, id);
	    if (st.executeUpdate() == 0) {
		throw new DbException("id não existe");
	    };
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}

    }

    @Override
    public Modelo findById(Long id) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "select * from " + tableName + " where id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setLong(1, id);
	    rs = st.executeQuery();
	    if(rs.next()) {
		return new Modelo(rs.getLong("id"), rs.getString("nome"));
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
    public List<Modelo> findAll() {
	PreparedStatement st = null;
	ResultSet rs = null;
	List <Modelo> modelos = new ArrayList<>();
	String sql = "select * from " + tableName;
	try {
	    st = conn.prepareStatement(sql);
	    rs = st.executeQuery();
	    while(rs.next()) {
		modelos.add(new Modelo(rs.getLong("id"), rs.getString("nome")));
	    }
	    return modelos;
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }

}
