package edu.eetac.dsa.flatmates.dao;

/**
 * Created by Admin on 23/11/2015.
 */
public class TareasDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_TAREA = "insert into tareas (id, grupoid, userid, tarea, image, punts) values (UNHEX(?), unhex(?), ?, ?)";
    public final static String GET_TAREA_BY_ID = "select hex(t.id) as id, hex(t.userid) as userid, t.grupoid, t.tarea, t.image, t.punts, u.loginid from tares t, users u where t.id=unhex(?) and u.id = t.userid";
    public final static String GET_TAREA = "select hex(id) as id, hex(userid) as userid, hex(grupoid) as grupoid, tarea, image, punts from tareas where grupoid = ?";
    public final static String UPDATE_IMAGE = "update tareas set image=? ";
    public final static String UPDATE_PUNTOS = "update tareas set punts = ?";
    public final static String DELETE_TAREA = "delete from tareas where id=unhex(?)";
}
