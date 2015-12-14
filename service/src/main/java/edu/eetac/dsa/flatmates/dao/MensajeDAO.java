package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionMensaje;
import edu.eetac.dsa.flatmates.entity.Mensaje;

import java.sql.SQLException;

/**
 * Created by Admin on 22/11/2015.
 */
public interface MensajeDAO {
    public Mensaje createMensaje(String userid, String content) throws SQLException;
    public Mensaje getMensajeById(String id) throws SQLException;
    public ColeccionMensaje getMensaje(long timestamp, boolean before) throws SQLException;
    public Mensaje updateMensaje(String id, String content) throws SQLException;
    public boolean deleteMensaje(String id) throws SQLException;
}
