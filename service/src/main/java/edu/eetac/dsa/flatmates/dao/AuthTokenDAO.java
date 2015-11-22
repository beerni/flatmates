package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.auth.UserInfo;
import edu.eetac.dsa.flatmates.entity.AuthToken;

import java.sql.SQLException;

/**
 * Created by Admin on 10/11/2015.
 */
public interface AuthTokenDAO {
    public UserInfo getUserByAuthToken(String token) throws SQLException;
    public AuthToken createAuthToken(String userid) throws SQLException;
    public void deleteToken(String userid) throws  SQLException;
}
