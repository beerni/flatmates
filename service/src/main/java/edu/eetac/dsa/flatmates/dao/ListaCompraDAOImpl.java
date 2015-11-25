package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionListaCompra;
import edu.eetac.dsa.flatmates.entity.ListaCompra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Admin on 24/11/2015.
 */
public class ListaCompraDAOImpl implements ListaCompraDAO {
    @Override
    public ListaCompra createLista(String item, String grupoid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ListaCompraDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(ListaCompraDAOQuery.CREATE_LISTA);
            stmt.setString(1, id);
            stmt.setString(2, item);
            stmt.setString(3, grupoid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getListaById(id);    }

    @Override
    public ListaCompra getListaById(String id) throws SQLException {
        ListaCompra listaCompra = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ListaCompraDAOQuery.GET_LISTA_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                listaCompra = new ListaCompra();
                listaCompra.setId(rs.getString("id"));
                listaCompra.setItem(rs.getString("item"));
                listaCompra.setGrupoid(rs.getString("grupoid"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return listaCompra;
    }

    @Override
    public ColeccionListaCompra getLista(String grupoid) throws SQLException {
        ColeccionListaCompra coleccionListaCompra = new ColeccionListaCompra();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(ListaCompraDAOQuery.GET_LISTA);
            stmt.setString(1, grupoid);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                ListaCompra listaCompra = new ListaCompra();
                listaCompra.setId(rs.getString("id"));
                listaCompra.setItem(rs.getString("item"));
                listaCompra.setGrupoid(rs.getString("grupoid"));
                coleccionListaCompra.getListaCompras().add(listaCompra);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return coleccionListaCompra;
    }

    @Override
    public ListaCompra updateLista(String id, String item) throws SQLException {
        ListaCompra listaCompra = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ListaCompraDAOQuery.UPDATE_LISTA);
            stmt.setString(1, item);
            stmt.setString(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                listaCompra = getListaById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return listaCompra;
    }

    @Override
    public boolean deleteLista(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ListaCompraDAOQuery.DELETE_LISTA);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();
            if (connection != null)
                connection.close();
        }
    }
}
