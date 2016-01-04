package edu.eetac.dsa.flatmates.dao;


import edu.eetac.dsa.flatmates.entity.ColeccionTareas;
import edu.eetac.dsa.flatmates.entity.RelacionPuntosTareas;
import edu.eetac.dsa.flatmates.entity.tareas;

import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by Admin on 28/11/2015.
 */
public interface TareasDAO {
    public tareas createTareas(String grupoid, String tarea) throws SQLException;
    public tareas getTareadById(String id, String grupoid) throws SQLException;
    public ColeccionTareas getTareas(String grupoid) throws SQLException;
    public boolean selectTarea(String idg, String idt, String userid) throws SQLException;
    public boolean deleteTarea(String id, String idt) throws SQLException;
    public tareas updateTarea(String id, String idg, InputStream imagen, String userid) throws SQLException;
    public boolean pointsTarea(String idg, String idt, String userid, int points) throws SQLException;
    public RelacionPuntosTareas getRelation(String userid, String tareaid) throws SQLException;
}
