package com.ray.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import com.ray.db.DB;
import com.ray.db.DbException;
import com.ray.model.dao.PedidoRepository;
import com.ray.model.entities.Pedido;

public class PedidoDaoJdbc implements PedidoRepository {

    private Connection conn;

    public PedidoDaoJdbc(Connection conn) {
	this.conn = conn;
    }

    /**
     * Retorna o cliente salvo do banco do de dados
     */
    @Override
    public boolean save(Pedido pedido) {
	PreparedStatement st = null;
	ResultSet rs = null;
	String sql = "insert into pedidos (cliente_id, order_time) values (?, ?)";
	try {
	    Calendar calendar = Calendar.getInstance();
	    java.util.Date currentTime = calendar.getTime();
	    long time = currentTime.getTime();
	    st = conn.prepareStatement(sql);
	    st.setLong(1, pedido.getCliente().getId());
	    st.setTimestamp(2, new Timestamp(time));
	    if (st.executeUpdate() > 0) {
		return true;
	    };
	} catch (SQLException e) {
	    throw new DbException(e.getMessage());
	} finally {
	    DB.closeStatement(st);
	    DB.closeResultSet(rs);
	}
	return false;
    }

}
