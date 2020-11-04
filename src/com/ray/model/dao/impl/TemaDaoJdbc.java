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
import com.ray.model.dao.TemaRepository;
import com.ray.model.entities.Tema;

public class TemaDaoJdbc implements TemaRepository {

    private Connection conn;

    public TemaDaoJdbc(Connection conn) {
	this.conn = conn;
    }

    /**
     * Retorna o cliente salvo do banco do de dados
     */
    @Override
    public Tema save(Tema tema) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "insert into temas (nome) values (?)";
	try {
	    st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    st.setString(1, tema.getNome());
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
    public Tema update(Tema tema) {
	PreparedStatement st = null;
	String sql = "update temas set nome = ? where id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setString(1, tema.getNome());
	    st.setLong(2, tema.getId());
	    st.executeUpdate();
	    return this.findById(tema.getId());
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }

    @Override
    public void deleteById(Long id) {
	PreparedStatement st = null;
	String sql = "delete from temas where id = ?";
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
    public Tema findById(Long id) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "select * from temas where id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setLong(1, id);
	    rs = st.executeQuery();
	    if(rs.next()) {
		return new Tema(rs.getLong("id"), rs.getString("nome"));
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
    public List<Tema> findAll() {
	PreparedStatement st = null;
	ResultSet rs = null;
	List <Tema> clientes = new ArrayList<>();
	String sql = "select * from temas";
	try {
	    st = conn.prepareStatement(sql);
	    rs = st.executeQuery();
	    while(rs.next()) {
		clientes.add(new Tema(rs.getLong("id"), rs.getString("nome")));
	    }
	    return clientes;
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }

}
