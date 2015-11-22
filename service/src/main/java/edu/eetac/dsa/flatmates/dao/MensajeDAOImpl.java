package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionMensaje;
import edu.eetac.dsa.flatmates.entity.mensaje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Admin on 22/11/2015.
 */
public class MensajeDAOImpl implements MensajeDAO {
    @Override
    public mensaje createMensaje(String userid, String subject, String content) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(MensajeDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(MensajeDAOQuery.CREATE_MENSAJE);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, subject);
            stmt.setString(4, content);
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
        return getMensajeById(id);
    }

    @Override
    public mensaje getMensajeById(String id) throws SQLException {
        mensaje Mensaje = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(MensajeDAOQuery.GET_MENSAJE_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Mensaje = new mensaje();
                Mensaje.setId(rs.getString("id"));
                Mensaje.setUserid(rs.getString("userid"));
                Mensaje.setLoginid(rs.getString("loginid"));
                Mensaje.setSubject(rs.getString("subject"));
                Mensaje.setMensaje(rs.getString("mensaje"));
                Mensaje.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                Mensaje.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return Mensaje;
    }

    @Override
    public ColeccionMensaje getMensaje() throws SQLException {
        ColeccionMensaje coleccionMensaje = new ColeccionMensaje();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(MensajeDAOQuery.GET_MENSAJES);
            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                mensaje Mensaje = new mensaje();
                Mensaje.setId(rs.getString("id"));
                Mensaje.setUserid(rs.getString("userid"));
                Mensaje.setSubject(rs.getString("subject"));
                Mensaje.setMensaje(rs.getString("mensaje"));
                Mensaje.setLoginid(rs.getString("loginid"));
                Mensaje.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                Mensaje.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    coleccionMensaje.setNewestTimestamp(Mensaje.getLastModified());
                    first = false;
                }
                coleccionMensaje.setOldestTimestamp(Mensaje.getLastModified());
                coleccionMensaje.getMensajes().add(Mensaje);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return coleccionMensaje;
    }

    @Override
    public mensaje updateMensaje(String id, String subject, String content) throws SQLException {
        mensaje Mensaje = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(MensajeDAOQuery.UPDATE_MENSAJE);
            stmt.setString(1, subject);
            stmt.setString(2, content);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                Mensaje = getMensajeById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return Mensaje;
    }

    @Override
    public boolean deleteMensaje(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(MensajeDAOQuery.DELETE_MENSAJE);
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
