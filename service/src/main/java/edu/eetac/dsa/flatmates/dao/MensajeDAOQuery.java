package edu.eetac.dsa.flatmates.dao;

/**
 * Created by Admin on 22/11/2015.
 */
public interface MensajeDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_MENSAJE = "insert into Mensaje (id, userid, subject, Mensaje) values (UNHEX(?), unhex(?), ?, ?)";
    public final static String GET_MENSAJE_BY_ID = "select hex(m.id) as id, hex(m.userid) as userid, m.subject, m.Mensaje, m.creation_timestamp, m.last_modified, u.loginid from Mensaje m, users u where m.id=unhex(?) and u.id = m.userid";
    public final static String GET_MENSAJES = "select hex(id) as id, hex(m.userid) as userid, m.subject, m.Mensaje, m.creation_timestamp, m.last_modified, u.loginid from Mensaje m, users u";
    public final static String UPDATE_MENSAJE = "update Mensaje set subject=?, content=? where id=unhex(?) ";
    public final static String DELETE_MENSAJE = "delete from Mensaje where id=unhex(?)";
}
