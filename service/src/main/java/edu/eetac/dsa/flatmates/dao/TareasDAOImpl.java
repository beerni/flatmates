package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionTareas;
import edu.eetac.dsa.flatmates.entity.tareas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Admin on 28/11/2015.
 */
public class TareasDAOImpl implements TareasDAO{
    @Override
    public tareas createTareas(String grupoid, String tarea) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TareasDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(TareasDAOQuery.CREATE_TAREA);
            stmt.setString(1, id);
            stmt.setString(2, grupoid);
            stmt.setString(3, tarea);
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
        return getTareadById(id, grupoid);
    }

    @Override
    public tareas getTareadById(String id, String grupoid) throws SQLException {
        tareas Tarea = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TareasDAOQuery.GET_TAREA_BY_ID);
            stmt.setString(1, id);
            stmt.setString(2, grupoid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Tarea = new tareas();
                Tarea.setId(rs.getString("id"));
                Tarea.setUserid(rs.getString("userid"));
                Tarea.setTarea(rs.getString("tarea"));
                Tarea.setImage(rs.getString("Image"));
                Tarea.setPuntos(rs.getInt("punts"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return Tarea;
    }

    @Override
    public ColeccionTareas getTareas(String grupoid) throws SQLException {
        ColeccionTareas coleccionTareas = new ColeccionTareas();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(TareasDAOQuery.GET_TAREA);
            stmt.setString(1, grupoid);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tareas Tarea = new tareas();
                Tarea.setId(rs.getString("id"));
                Tarea.setUserid(rs.getString("userid"));
                Tarea.setTarea(rs.getString("tarea"));
                coleccionTareas.getTareas().add(Tarea);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return coleccionTareas;
    }

    @Override
    public boolean deleteTarea(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TareasDAOQuery.DELETE_TAREA);
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
