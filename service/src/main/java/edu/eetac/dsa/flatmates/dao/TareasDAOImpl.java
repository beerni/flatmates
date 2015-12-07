package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionTareas;
import edu.eetac.dsa.flatmates.entity.RelacionPuntosTareas;
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
                Tarea.setImage(rs.getString("imagen"));
                Tarea.setPuntos(rs.getInt("punts"));
                Tarea.setHecho(rs.getBoolean("hecho"));
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
                Tarea.setHecho(rs.getBoolean("hecho"));
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
    public boolean selectTarea(String idg, String idt, String userid) throws SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();


            stmt = connection.prepareStatement(TareasDAOQuery.SELECT_TAREA);
            stmt.setString(1, userid);
            stmt.setString(2, idg);
            stmt.setString(3, idt);
            stmt.executeUpdate();

            stmt.close();
            return true;

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public boolean pointsTarea(String idg, String idt, String userid, int points) throws SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TareasDAOQuery.UPDATE_PUNTOS);
            stmt.setInt(1, points);
            stmt.setString(2, idt);
            stmt.setString(3, idg);
            stmt.executeUpdate();

            tareas Tareas = getTareadById(idt, idg);

            stmt.close();

            stmt = connection.prepareStatement(TareasDAOQuery.INSERT_RELATION);
            stmt.setString(1, userid);
            stmt.setString(2, idt);
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement(UserDAOQuery.SET_PUNTOS);
            stmt.setInt(1, points);
            stmt.setString(2, Tareas.getUserid());
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement(GrupoDAOQuery.SET_PUNTOS);
            stmt.setInt(1, points);
            stmt.setString(2, Tareas.getUserid());
            stmt.setString(3, idg);
            stmt.executeUpdate();
            stmt.close();
            return true;

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public boolean deleteTarea(String id, String idt) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TareasDAOQuery.DELETE_TAREA);
            stmt.setString(1, idt);
            stmt.setString(2, id);

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
    @Override
    public tareas updateTarea(String id, String idg, String uuid_imagen, String userid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        tareas Tareas = null;
        try {
            connection = Database.getConnection();
            Tareas = getTareadById(id, idg);
            if (!Tareas.isHecho()) {
                stmt = connection.prepareStatement(UserDAOQuery.SET_TAREAS);
                stmt.setString(1, userid);
                stmt.executeUpdate();
                stmt.close();
            }
            stmt = connection.prepareStatement(TareasDAOQuery.UPDATE_IMAGE);
            stmt.setString(1, uuid_imagen);
            stmt.setString(1, uuid_imagen);
            stmt.setString(2, id);
            stmt.setString(3, idg);
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }

        return getTareadById(id, idg);
    }

    @Override
    public RelacionPuntosTareas getRelation(String userid, String tareaid) throws SQLException {
        RelacionPuntosTareas relacionPuntosTareas = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TareasDAOQuery.GET_RELATION);
            stmt.setString(1, userid);
            stmt.setString(2, tareaid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                relacionPuntosTareas = new RelacionPuntosTareas();
                relacionPuntosTareas.setUserid(rs.getString("userid"));
                relacionPuntosTareas.setIdtarea(rs.getString("idtarea"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return relacionPuntosTareas;
    }
}
