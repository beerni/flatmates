package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionMensaje;
import edu.eetac.dsa.flatmates.entity.Mensaje;

import java.sql.*;

/**
 * Created by Admin on 22/11/2015.
 */
public class MensajeDAOImpl implements MensajeDAO {
    @Override
    public Mensaje createMensaje(String userid, String content) throws SQLException {
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
            stmt.setString(3, content);
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
    public Mensaje getMensajeById(String id) throws SQLException {
        Mensaje Mensaje = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(MensajeDAOQuery.GET_MENSAJE_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Mensaje = new Mensaje();
                Mensaje.setId(rs.getString("id"));
                Mensaje.setUserid(rs.getString("userid"));
                Mensaje.setLoginid(rs.getString("loginid"));
                Mensaje.setContent(rs.getString("content"));
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
    public ColeccionMensaje getMensaje(int pag, boolean before) throws SQLException {
        ColeccionMensaje coleccionMensaje = new ColeccionMensaje();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            int pa = pag;
            int pa1 = pag;
            int pa2=pag;
            connection = Database.getConnection();
            stmt = connection.prepareStatement(MensajeDAOQuery.GET_COUNT);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            double pagin = rs.getInt("count");
            int pag_total = 0;
            double total = pagin/5;
            int paginas = (int)pagin/5;
            if (total > paginas)
                pag_total = paginas;
            else if (total == paginas){
                pag_total = paginas-1;
            }
            else
                pag_total = paginas;
            stmt.close();
            stmt = connection.prepareStatement(MensajeDAOQuery.GET_MENSAJES);


            if (!before){
                if (pag==0)
                {
                    pa2 = 0;
                    pa1 = pa1+1;
                }
                else
                {
                    pa2=pa2-1;
                    pag = pag - 1;

                }
            }
            else{
                if (pag_total != pag)
                    pa1=pa1+1;
            }
            pag = pag * 5;
            stmt.setInt(1, pag);
            rs = stmt.executeQuery();

            boolean first = true;
            while (rs.next()) {
                Mensaje Mensaje = new Mensaje();
                Mensaje.setId(rs.getString("id"));
                Mensaje.setUserid(rs.getString("userid"));
                Mensaje.setContent(rs.getString("content"));
                Mensaje.setLoginid(rs.getString("loginid"));
                Mensaje.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                Mensaje.setLastModified(rs.getTimestamp("last_modified").getTime());

                if (first) {
                    coleccionMensaje.setNewestTimestamp(Mensaje.getLastModified());
                    first = false;
                }
                Mensaje.setPag(pa);
                coleccionMensaje.setPag(pa1);
                coleccionMensaje.setPagbefore(pa2);
                coleccionMensaje.setPagtotal(pag_total);
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
    public Mensaje updateMensaje(String id, String content) throws SQLException {
        Mensaje Mensaje = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(MensajeDAOQuery.UPDATE_MENSAJE);
            stmt.setString(1, content);
            stmt.setString(2, id);

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