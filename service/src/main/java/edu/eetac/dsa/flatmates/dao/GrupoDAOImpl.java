package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionGrupo;
import edu.eetac.dsa.flatmates.entity.Grupo;
import edu.eetac.dsa.flatmates.entity.GrupoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Admin on 21/11/2015.
 */
public class GrupoDAOImpl implements GrupoDAO {
    @Override
    public Grupo createGrupo(String userid, String nombre, String info) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(GrupoDAOQuery.CREATE_GRUPO);
            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, info);
            stmt.setString(4, userid);
            stmt.executeUpdate();


            stmt.close();

            stmt = connection.prepareStatement(GrupoDAOQuery.ADD_GRUPO);
            stmt.setString(1, id);
            stmt.setString(2, userid);
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
        return getGrupoById(id);
    }


    @Override
    public Grupo getGrupoById(String id) throws SQLException {
        Grupo grupo = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GrupoDAOQuery.GET_GRUPO_BY_ID);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                grupo = new Grupo();
                grupo.setId(rs.getString("id"));
                grupo.setNombre(rs.getString("nombre"));
                grupo.setInfo(rs.getString("info"));
                grupo.setAdmin(rs.getString("admin"));
                grupo.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                grupo.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
            rs.close();
            stmt = connection.prepareStatement(GrupoDAOQuery.GET_USER);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                GrupoUsuario grupoUsuario = new GrupoUsuario();
                grupoUsuario.setUserid(rs.getString("userid"));
                grupoUsuario.setGrupoid(rs.getString("grupoid"));
                grupoUsuario.setPuntos(rs.getInt("puntos"));
                grupoUsuario.setLoginid(rs.getString("loginid"));
                grupo.getUsuarios().add(grupoUsuario);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return grupo;
    }

    @Override
    public ColeccionGrupo getGrupos() throws SQLException {
        ColeccionGrupo coleccionGrupo = new ColeccionGrupo();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.GET_GRUPOS);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Grupo grupo = new Grupo();
                grupo.setId(rs.getString("id"));
                grupo.setNombre(rs.getString("nombre"));
                grupo.setInfo(rs.getString("info"));
                grupo.setAdmin(rs.getString("admin"));
                grupo.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                grupo.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    coleccionGrupo.setNewestTimestamp(grupo.getLastModified());
                    first = false;
                }
                coleccionGrupo.setOldestTimestamp(grupo.getLastModified());
                coleccionGrupo.getGrupos().add(grupo);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return coleccionGrupo;
    }

    @Override
    public boolean deleteGrupo(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GrupoDAOQuery.DELETE_GRUPO);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public boolean añadirusuariosalGrupo(String id, String idu) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();


            stmt = connection.prepareStatement(GrupoDAOQuery.ADD_GRUPO);
            stmt.setString(1, id);
            stmt.setString(2, idu);
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
    public boolean eliminarusuarioGrupo(String id, String idu) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();


            stmt = connection.prepareStatement(GrupoDAOQuery.DEL_GRUPO);
            stmt.setString(1, id);
            stmt.setString(2, idu);
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
    public GrupoUsuario getPuntos(String loginid, String grupoid) throws SQLException {
        GrupoUsuario grupoUsuario = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GrupoDAOQuery.GET_PUNTOS);
            stmt.setString(1, loginid);
            stmt.setString(2, grupoid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                grupoUsuario = new GrupoUsuario();
                grupoUsuario.setUserid(rs.getString("userid"));
                grupoUsuario.setGrupoid(rs.getString("grupoid"));
                grupoUsuario.setPuntos(rs.getInt("puntos"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return grupoUsuario;
    }

    @Override
    public GrupoUsuario updatePuntos(String loginid, String grupoid, int puntos) throws SQLException {
        GrupoUsuario grupoUsuario = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GrupoDAOQuery.SET_PUNTOS);
            stmt.setInt(1, puntos);
            stmt.setString(2, loginid);
            stmt.setString(3, grupoid);
            int rows = stmt.executeUpdate();
            if (rows == 1)
                grupoUsuario = getPuntos(loginid, grupoid);
        } catch (SQLException ex){
            throw ex;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return grupoUsuario;
    }

    @Override
    public GrupoUsuario getGrupoUserById(String id, String loginid) throws SQLException {
        GrupoUsuario grupoUsuario = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GrupoDAOQuery.GET_GRUPOUSER);
            stmt.setString(1, id);
            stmt.setString(2, loginid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                grupoUsuario = new GrupoUsuario();
                grupoUsuario.setGrupoid(rs.getString("grupoid"));
                grupoUsuario.setUserid(rs.getString("userid"));
                grupoUsuario.setPuntos(rs.getInt("puntos"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return grupoUsuario;
    }

    @Override
    public GrupoUsuario getUserGrupoById(String id) throws SQLException {
        GrupoUsuario grupoUsuario = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GrupoDAOQuery.GET_USERGRUPO);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                grupoUsuario = new GrupoUsuario();
                grupoUsuario.setGrupoid(rs.getString("grupoid"));
                grupoUsuario.setUserid(rs.getString("userid"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return grupoUsuario;
    }


}
