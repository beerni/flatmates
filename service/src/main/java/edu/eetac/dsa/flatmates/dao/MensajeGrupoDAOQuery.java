package edu.eetac.dsa.flatmates.dao;

/**
 * Created by Admin on 22/11/2015.
 */
public interface MensajeGrupoDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_MENSAJE = "insert into mensajegrupo (id, grupoid, userid, subject, content) values (UNHEX(?), unhex(?), unhex(?), ?, ?)";
    public final static String GET_MENSAJE_BY_ID = "select hex(id) as id,hex(grupoid) as grupoid, hex(userid) as userid, subject, mensaje, creation_timestamp, last_modified from mensajegrupo where id=unhex(?)";
    public final static String GET_MENSAJES = "select hex(id) as id, hex(grupoid) as grupoid, hex(userid) as userid, subject, mensaje, creation_timestamp, last_modified from mensajegrupo where grupoid=unhex(?)";
    public final static String UPDATE_MENSAJE = "update mensajegrupo set subject=?, content=? where id=unhex(?) ";
    public final static String DELETE_MENSAJE = "delete from mensajegrupo where id=unhex(?)";
}
