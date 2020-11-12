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
import com.ray.model.dao.CanecaRepository;
import com.ray.model.dao.ImageRepository;
import com.ray.model.dao.RepositoryFactory;
import com.ray.model.entities.Arquivo;
import com.ray.model.entities.Caneca;

public class ArquivoDaoJdbc implements ImageRepository {

    private Connection conn;
    private String tableName = "arquivos";
    private CanecaRepository canecaRepository;
    
    public ArquivoDaoJdbc(Connection conn) {
	this.conn = conn;
	this.canecaRepository = RepositoryFactory.createCanecaDao();
    }

    /**
     * Retorna a imagem que foi salva no banco de dados
     */
    @Override
    public Arquivo save(Arquivo arquivo) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "insert into " + tableName + " (image, base64, miniatura, content_type, id_caneca) values (?, ?, ?, ?, ?)";
	try {
	    st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    st.setBlob(1, arquivo.getInputStream());
	    st.setString(2, arquivo.getBase64());
	    st.setString(3, arquivo.getMiniatura());
	    st.setString(4, arquivo.getContentType());
	    st.setLong(5, arquivo.getCaneca().getId());
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
    public Arquivo update(Arquivo arquivo, boolean updateInputStream) {
	PreparedStatement st = null;
	String sql = updateInputStream ? "update " + tableName + " set base64 = ?, miniatura = ?, content_type = ?, image = ? where id = ?" :
	    "update " + tableName + " set base64 = ?, miniatura = ?, content_type = ? where id = ?";
	try {
	    int pos = 5;
	    st = conn.prepareStatement(sql);
	    st.setString(1, arquivo.getBase64());
	    st.setString(2, arquivo.getMiniatura());
	    st.setString(3, arquivo.getContentType());
	    if(updateInputStream) {
		st.setBlob(4, arquivo.getInputStream());
	    }else {
		pos = 4;
	    }
	    st.setLong(pos, arquivo.getId());
	    st.executeUpdate();
	    return this.findById(arquivo.getId());
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }

    @Override
    public void deleteById(Long id) {
	PreparedStatement st = null;
	String sql = "delete from " + tableName + " where id = ? and id <> 0;";
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
    public Arquivo findById(Long id) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "select * from " + tableName + " where id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setLong(1, id);
	    rs = st.executeQuery();
	    if(rs.next()) {
		return setNewImage(rs);
	    }else {
		return null;
	    }
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }
    
    private Arquivo setNewImage(ResultSet rs) throws SQLException {
	Caneca c = canecaRepository.findById(rs.getLong("id_caneca"));
	return new Arquivo(rs.getLong("id"), rs.getBinaryStream("image"), rs.getString("base64"), rs.getString("miniatura"), rs.getString("content_type"), c);
    }

    @Override
    public List<Arquivo> findAll(Long canecaId) {
	PreparedStatement st = null;
	ResultSet rs = null;
	List <Arquivo> modelos = new ArrayList<>();
	String sql = "select * from arquivos where id_caneca = " + canecaId;
	try {
	    st = conn.prepareStatement(sql);
//	    st.setLong(1, canecaId);
	    rs = st.executeQuery();
	    while(rs.next()) {
		modelos.add(setNewImage(rs));
	    }
	    return modelos;
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	}
    }

}
