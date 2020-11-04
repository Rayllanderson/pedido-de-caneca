package com.ray.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
	String sql = "insert into clientes (nome, telefone) values (?, ?)";
	try {
	    st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    st.setString(1, cliente.getNome());
	    st.setString(2, cliente.getTelefone());
	    if (st.executeUpdate() > 0) {
		ResultSet rs = st.getGeneratedKeys();
		if (rs.next()) {
		    cliente.setId(rs.getLong(1));
		}
		DB.closeResultSet(rs);
		return cliente; //return findById depois
	    }
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
	return null;
    }

    @Override
    public Cliente update(Cliente cliente) {
	PreparedStatement st = null;
	String sql = "update clientes set nome = ?, telefone = ? where id = ?";
	try {
	    st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    st.setString(1, cliente.getNome());
	    st.setString(2, cliente.getTelefone());
	    st.setLong(3, cliente.getId());
	    st.executeUpdate();
//	    return this.findById(cliente.getId());
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
	return cliente;
    }

    @Override
    public void deleteById(Long id) {
	// TODO Auto-generated method stub

    }

    @Override
    public Cliente findById(Long id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Cliente> findAll() {
	// TODO Auto-generated method stub
	return null;
    }

}
