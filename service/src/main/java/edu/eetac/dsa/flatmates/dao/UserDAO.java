package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.PuntosTotales;
import edu.eetac.dsa.flatmates.entity.User;

import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by Admin on 08/11/2015.
 */
public interface UserDAO {
    public User createUser(String loginid, String password, String email, String fullname, String info, boolean sexo) throws SQLException, UserAlreadyExistsException;

    public User updateProfile(String id, String email, String fullname, String info) throws SQLException;

    public User getUserById(String id) throws SQLException;

    public User getUserByLoginid(String loginid) throws SQLException;

    public boolean deleteUser(String id) throws SQLException;

    public boolean checkPassword(String id, String password) throws SQLException;

    public PuntosTotales getPuntos(String loginid) throws SQLException;

    public PuntosTotales updatePuntos(String loginid, int puntos) throws SQLException;

}
