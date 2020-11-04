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
import com.ray.model.dao.ImageRepository;
import com.ray.model.entities.Image;

public class ImageDaoJdbc implements ImageRepository {

    private Connection conn;
    private String tableName = "image";
    
    public ImageDaoJdbc(Connection conn) {
	this.conn = conn;
    }

    /**
     * Retorna o cliente salvo do banco do de dados
     */
    @Override
    public Image save(Image image) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "insert into " + tableName + " (image, base64, miniatura) values (?, ?, ?)";
	try {
	    st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    st.setBlob(1, image.getInputStream());
	    st.setString(2, image.getBase64());
	    st.setString(3, image.getMiniatura());
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
    public Image update(Image image) {
	PreparedStatement st = null;
	String sql = "update " + tableName + " set image = ?, set base64 = ?, set miniatura = ? where id = ?";
	try {
	    st = conn.prepareStatement(sql);
	    st.setBlob(1, image.getInputStream());
	    st.setString(2, image.getBase64());
	    st.setString(3, image.getMiniatura());
	    st.setLong(4, image.getId());
	    st.executeUpdate();
	    return this.findById(image.getId());
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
    public Image findById(Long id) {
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
    
    private Image setNewImage(ResultSet rs) throws SQLException {
	return new Image(rs.getLong("id"), rs.getBinaryStream("image"), rs.getString("base64"), rs.getString("miniatura"));
    }

    @Override
    public List<Image> findAll() {
	PreparedStatement st = null;
	ResultSet rs = null;
	List <Image> modelos = new ArrayList<>();
	String sql = "select * from " + tableName;
	try {
	    st = conn.prepareStatement(sql);
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
