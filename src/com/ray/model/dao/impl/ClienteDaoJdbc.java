package com.ray.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import com.ray.db.DB;
import com.ray.db.DbException;
import com.ray.model.dao.ClienteRepository;
import com.ray.model.entities.Cliente;

public class ClienteDaoJdbc implements ClienteRepository {

    private Connection conn;

    public ClienteDaoJdbc(Connection conn) {
	this.conn = conn;
    }

    /**
     * Retorna o cliente salvo do banco do de dados
     */
    @Override
    public Cliente save(Cliente cliente) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "insert into clientes (nome, telefone) values (?, ?)";
	try {
	    st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    st.setString(1, cliente.getNome());
	    st.setString(2, cliente.getTelefone());
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
    public Cliente update(Cliente cliente) {
	PreparedStatement st = null;
	String sql = "update clientes set nome = ?, telefone = ? where id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setString(1, cliente.getNome());
	    st.setString(2, cliente.getTelefone());
	    st.setLong(3, cliente.getId());
	    st.executeUpdate();
	    return this.findById(cliente.getId());
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }

    @Override
    public void deleteById(Long id) {
	PreparedStatement st = null;
	String sql = "delete from clientes where id = ?";
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
    public Cliente findById(Long id) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "select * from clientes where id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setLong(1, id);
	    rs = st.executeQuery();
	    if(rs.next()) {
		return new Cliente(rs.getLong("id"), rs.getString("nome"), rs.getString("telefone"));
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
    public List<Cliente> findAll() {
	PreparedStatement st = null;
	ResultSet rs = null;
	List <Cliente> clientes = new ArrayList<>();
	String sql = "select * from clientes";
	try {
	    st = conn.prepareStatement(sql);
	    rs = st.executeQuery();
	    while(rs.next()) {
		clientes.add(new Cliente(rs.getLong("id"), rs.getString("nome"), rs.getString("telefone")));
	    }
	    return clientes;
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }

}
