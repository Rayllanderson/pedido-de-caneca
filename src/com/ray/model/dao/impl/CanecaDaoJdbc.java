package com.ray.model.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ray.db.DB;
import com.ray.db.DbException;
import com.ray.model.dao.CanecaRepository;
import com.ray.model.entities.Caneca;
import com.ray.model.entities.Cliente;
import com.ray.model.entities.Image;
import com.ray.model.entities.Modelo;
import com.ray.model.entities.Tema;

public class CanecaDaoJdbc implements CanecaRepository {
    
    private Connection conn;
    private String tableName = "canecas";

    public CanecaDaoJdbc(Connection conn) {
	this.conn = conn;
    }

    /**
     * Retorna a imagem que foi salva no banco de dados
     */
    @Override
    public Caneca save(Caneca caneca) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "insert into " + tableName + " (quantidade, id_foto, id_modelo, id_tema, id_cliente, descricao) values (?, ?, ?, ?, ?, ?)";
	try {
	    st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    st.setInt(1, caneca.getQuantidade());
	    st.setLong(2, caneca.getImage().getId());
	    st.setLong(3, caneca.getModelo().getId());
	    st.setLong(4, caneca.getTema().getId());
	    st.setLong(5, caneca.getCliente().getId());
	    st.setString(6, caneca.getDescricao());
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
    public Caneca update(Caneca caneca) {
	PreparedStatement st = null;
	String sql = "update " + tableName
		+ " set quantidade = ?, id_foto = ?, id_modelo = ?, id_tema = ?, id_cliente = ?, descricao = ? where id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setInt(1, caneca.getQuantidade());
	    st.setLong(2, caneca.getImage().getId());
	    st.setLong(3, caneca.getModelo().getId());
	    st.setLong(4, caneca.getTema().getId());
	    st.setLong(5, caneca.getCliente().getId());
	    st.setString(6, caneca.getDescricao());
	    st.setLong(7, caneca.getId());
	    st.executeUpdate();
	    return this.findById(caneca.getId());
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
	    }
	    ;
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}

    }

    @Override
    public Caneca findById(Long id) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "select canecas.*, temas.nome as tema_nome, modelos.nome as modelo_nome, "
		+ "fotos.image as image, fotos.base64 as base64, fotos.miniatura as miniatura, "
		+ "clientes.nome as cli_nome, clientes.telefone as cli_tel from canecas "
		+ "inner join temas on id_tema = temas.id inner join modelos on id_modelo = modelos.id "
		+ "inner join fotos on id_foto = fotos.id inner join clientes on id_cliente = clientes.id "
		+ "where canecas.id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setLong(1, id);
	    rs = st.executeQuery();
	    if (rs.next()) {
		return setNewCaneca(rs);
	    } else {
		return null;
	    }
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }

    private Caneca setNewCaneca(ResultSet rs) throws SQLException {
	Tema tema = new Tema(rs.getLong("id_tema"), rs.getString("tema_nome"));
	Modelo modelo = new Modelo (rs.getLong("id_modelo"), rs.getString("modelo_nome"));
	Image foto = new Image(rs.getLong("id_foto"), rs.getBinaryStream("image"), rs.getString("base64"), rs.getString("miniatura"));
	Cliente cli = new Cliente(rs.getLong("id_cliente"), rs.getString("cli_nome"), rs.getString("cli_tel"));
	Caneca caneca = new Caneca();
	caneca.setId(rs.getLong("id"));
	caneca.setQuantidade(rs.getInt("quantidade"));
	caneca.setTema(tema);
	caneca.setModelo(modelo);
	caneca.setImage(foto);
	caneca.setCliente(cli);
	caneca.setDescricao(rs.getString("descricao"));
	return caneca;
    }
    
    private Caneca setAllCanecas(ResultSet rs) throws SQLException {
	Tema tema = new Tema(rs.getLong("id_tema"), rs.getString("tema_nome"));
	Modelo modelo = new Modelo (rs.getLong("id_modelo"), rs.getString("modelo_nome"));
	Image foto = new Image(rs.getLong("id_foto"), null, null, rs.getString("miniatura"));
	Cliente cli = new Cliente(rs.getLong("id_cliente"), rs.getString("cli_nome"), rs.getString("cli_tel"));
	Caneca caneca = new Caneca();
	caneca.setId(rs.getLong("id"));
	caneca.setQuantidade(rs.getInt("quantidade"));
	caneca.setTema(tema);
	caneca.setModelo(modelo);
	caneca.setImage(foto);
	caneca.setCliente(cli);
	caneca.setDescricao(rs.getString("descricao"));
	return caneca;
    }

    @Override
    public List<Caneca> findAll(Long clientId) {
	PreparedStatement st = null;
	ResultSet rs = null;
	List<Caneca> modelos = new ArrayList<>();
	String sql = "select canecas.*, temas.nome as tema_nome, modelos.nome as modelo_nome, "
		+ "fotos.miniatura as miniatura, "
		+ "clientes.nome as cli_nome, clientes.telefone as cli_tel from canecas "
		+ "inner join temas on id_tema = temas.id inner join modelos on id_modelo = modelos.id "
		+ "inner join fotos on id_foto = fotos.id inner join clientes on id_cliente = clientes.id "
		+ "where id_cliente = " + clientId;
	try {
	    st = conn.prepareStatement(sql);
	    rs = st.executeQuery();
	    while (rs.next()) {
		modelos.add(setAllCanecas(rs));
	    }
	    return modelos;
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }
}
