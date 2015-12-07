package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionGrupo;
import edu.eetac.dsa.flatmates.entity.Grupo;
import edu.eetac.dsa.flatmates.entity.GrupoUsuario;


import java.sql.SQLException;

/**
 * Created by Admin on 09/11/2015.
 */
public interface GrupoDAO {
    public Grupo createGrupo(String userid, String nombre, String info) throws SQLException;
    public Grupo getGrupoById(String id) throws SQLException;
    public ColeccionGrupo getGrupos() throws SQLException;
    public boolean deleteGrupo(String id) throws SQLException;
    public boolean añadirusuariosalGrupo(String id, String idu) throws SQLException;
    public boolean eliminarusuarioGrupo(String id, String idu) throws SQLException;
    public GrupoUsuario getPuntos(String loginid, String grupoid) throws SQLException;
    public GrupoUsuario updatePuntos(String loginid, String grupoid, int puntos) throws SQLException;
    public GrupoUsuario getGrupoUserById(String id, String loginid) throws SQLException;
    public GrupoUsuario getUserGrupoById(String loginid) throws SQLException;

}
