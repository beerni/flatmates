package edu.eetac.dsa.flatmates.dao;

/**
 * Created by Admin on 22/11/2015.
 */
public interface MensajeDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_MENSAJE = "insert into mensaje (id, userid, subject, mensaje) values (UNHEX(?), unhex(?), ?, ?)";
    public final static String GET_MENSAJE_BY_ID = "select hex(m.id) as id, hex(m.userid) as userid, m.subject, m.mensaje, m.creation_timestamp, m.last_modified, u.loginid from mensaje m, users u where m.id=unhex(?) and u.id = m.userid";
    public final static String GET_MENSAJES = "select hex(m.id) as id, hex(m.userid) as userid, m.subject, m.mensaje, m.creation_timestamp, m.last_modified, u.loginid from mensaje m, users u where u.id=m.userid";
    public final static String UPDATE_MENSAJE = "update mensaje set subject=?, mensaje=? where id=unhex(?) ";
    public final static String DELETE_MENSAJE = "delete from mensaje where id=unhex(?)";
}
