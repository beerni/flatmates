package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionMensajeGrupo;
import edu.eetac.dsa.flatmates.entity.MensajeGrupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Admin on 22/11/2015.
 */
public class MensajeGrupoDAOImpl implements MensajeGrupoDAO {
    @Override
    public MensajeGrupo createMensaje(String userid, String grupoid, String subject, String content) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(MensajeGrupoDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(MensajeGrupoDAOQuery.CREATE_MENSAJE);
            stmt.setString(1, id);
            stmt.setString(2, grupoid);
            stmt.setString(3, userid);
            stmt.setString(4, subject);
            stmt.setString(5, content);
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
    public MensajeGrupo getMensajeById(String id) throws SQLException {
        MensajeGrupo mensajeGrupo = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(MensajeGrupoDAOQuery.GET_MENSAJE_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mensajeGrupo = new MensajeGrupo();
                mensajeGrupo.setId(rs.getString("id"));
                mensajeGrupo.setGrupoid(rs.getString("grupoid"));
                mensajeGrupo.setUserid(rs.getString("userid"));
                mensajeGrupo.setSubject(rs.getString("subject"));
                mensajeGrupo.setMensaje(rs.getString("mensaje"));
                mensajeGrupo.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                mensajeGrupo.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return mensajeGrupo;
    }

    @Override
    public ColeccionMensajeGrupo getMensaje(String id) throws SQLException {
        ColeccionMensajeGrupo coleccionMensajeGrupo = new ColeccionMensajeGrupo();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(MensajeGrupoDAOQuery.GET_MENSAJES);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                MensajeGrupo mensajeGrupo = new MensajeGrupo();
                mensajeGrupo.setId(rs.getString("id"));
                mensajeGrupo.setGrupoid(rs.getString("grupoid"));
                mensajeGrupo.setUserid(rs.getString("userid"));
                mensajeGrupo.setSubject(rs.getString("subject"));
                mensajeGrupo.setMensaje(rs.getString("mensaje"));
                mensajeGrupo.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                mensajeGrupo.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    coleccionMensajeGrupo.setNewestTimestamp(mensajeGrupo.getLastModified());
                    first = false;
                }
                coleccionMensajeGrupo.setOldestTimestamp(mensajeGrupo.getLastModified());
                coleccionMensajeGrupo.getMensajesgrupo().add(mensajeGrupo);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return coleccionMensajeGrupo;
    }

    @Override
    public MensajeGrupo updateMensaje(String id, String subject, String content) throws SQLException {
        MensajeGrupo mensajeGrupo = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(MensajeGrupoDAOQuery.UPDATE_MENSAJE);
            stmt.setString(1, subject);
            stmt.setString(2, content);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                mensajeGrupo = getMensajeById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return mensajeGrupo;
    }

    @Override
    public boolean deleteMensaje(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(MensajeGrupoDAOQuery.DELETE_MENSAJE);
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
