package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionMensajeGrupo;
import edu.eetac.dsa.flatmates.entity.MensajeGrupo;

import java.sql.SQLException;

/**
 * Created by Admin on 22/11/2015.
 */
public interface MensajeGrupoDAO {
    public MensajeGrupo createMensaje(String userid, String grupoid, String subject, String content) throws SQLException;
    public MensajeGrupo getMensajeById(String id) throws SQLException;
    public ColeccionMensajeGrupo getMensaje(String id) throws SQLException;
    public MensajeGrupo updateMensaje(String id, String subject, String content) throws SQLException;
    public boolean deleteMensaje(String id) throws SQLException;
}
