package edu.eetac.dsa.flatmates.dao;

/**
 * Created by Admin on 23/11/2015.
 */
public class TareasDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_TAREA = "insert into tareas (id, grupoid, tarea, punts, hecho) values (UNHEX(?), unhex(?), ?, '0', 'false')";
    public final static String GET_TAREA_BY_ID = "select hex(id) as id, hex(userid) as userid, tarea, imagen, punts, hecho from tareas where id=unhex(?) and grupoid = unhex(?)";
    public final static String GET_TAREA_BY = "select hex(t.id) as id, hex(t.userid) as userid, t.tarea, t.imagen, t.punts, u.loginid from tareas t, users u where t.id=unhex(?) and u.id = t.userid";
    public final static String GET_TAREA = "select hex(id) as id, hex(userid) as userid, tarea, punts, hecho from tareas where grupoid = unhex(?)";
    public final static String UPDATE_IMAGE = "update tareas set imagen=?, hecho = 'true' where id = unhex(?) and grupoid = unhex(?)";
    public final static String UPDATE_PUNTOS = "update tareas set punts = punts + ? where id = unhex(?) and grupoid=unhex(?)";
    public final static String DELETE_TAREA = "delete from tareas where id=unhex(?) and grupoid=unhex(?)";
    public final static String SELECT_TAREA = "update tareas set userid = unhex(?) where grupoid = unhex(?) and id = unhex(?)";
    public final static String INSERT_RELATION = "insert into relacionPuntosTareas (userid, idtarea) values(unhex(?), unhex(?))";
    public final static String GET_RELATION = "select hex(userid) as userid, hex(idtarea) as idtarea from relacionPuntosTareas where userid = unhex(?) and idtarea= unhex(?)";
}
