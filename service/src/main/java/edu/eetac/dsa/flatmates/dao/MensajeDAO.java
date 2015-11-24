package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionMensaje;
import edu.eetac.dsa.flatmates.entity.Mensaje;

import java.sql.SQLException;

/**
 * Created by Admin on 22/11/2015.
 */
public interface MensajeDAO {
    public Mensaje createMensaje(String userid, String subject, String content) throws SQLException;
    public Mensaje getMensajeById(String id) throws SQLException;
    public ColeccionMensaje getMensaje() throws SQLException;
    public Mensaje updateMensaje(String id, String subject, String content) throws SQLException;
    public boolean deleteMensaje(String id) throws SQLException;
}
