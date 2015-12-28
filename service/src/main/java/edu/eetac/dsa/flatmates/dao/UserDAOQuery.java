package edu.eetac.dsa.flatmates.dao;

/**
 * Created by Admin on 09/11/2015.
 */
public interface UserDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_USER_HOMBRE = "insert into users (id, loginid, password, email, fullname, sexo, info, tareas, imagen, puntos) values (UNHEX(?), ?, UNHEX(MD5(?)), ?, ?, 'hombre', ?, '0',?, '0')";
    public final static String CREATE_USER_MUJER = "insert into users (id, loginid, password, email, fullname, sexo, info, tareas, imagen, puntos) values (UNHEX(?), ?, UNHEX(MD5(?)), ?, ?, 'mujer', ?, '0',?, '0')";
    public final static String UPDATE_USER = "update users set email=?, fullname=?, info=? where id=unhex(?)";
    public final static String UPDATE_PASSWORD = "update users set password=UNHEX(MD5(?)) where id=unhex(?)";
    public final static String ASSIGN_ROLE_REGISTERED = "insert into user_roles (userid, role) values (UNHEX(?), 'registered')";
    public final static String ASSIGN_ROLE_ADMIN = "insert into user_roles (userid, role) values (UNHEX(?), 'admin')";
    public final static String GET_USER_BY_ID = "select hex(id) as id, loginid, email, fullname, sexo, info, tareas, puntos, imagen from users where id=unhex(?)";
    public final static String GET_USER_BY_USERNAME = "select hex(id) as id, loginid, email, fullname, sexo, info, tareas, puntos, imagen from users where loginid=?";
    public final static String DELETE_USER = "delete from users where id=unhex(?)";
    public final static String GET_USERS_BY_LOGIN="SELECT hex(id) as id, loginid, email, fullname, sexo, info, tareas, puntos, imagen FROM users WHERE loginid LIKE ? ";
    public final static String GET_PASSWORD =  "select hex(password) as password from users where id=unhex(?)";
    public final static String GET_PUNTOS = "select loginid, puntos from users where id = unhex(?)";
    public final static String SET_PUNTOS = "update users set puntos= puntos + ? where id = unhex(?)";
    public final static String SET_TAREAS = "update users set tareas= tareas + 1 where id = unhex(?)";

}