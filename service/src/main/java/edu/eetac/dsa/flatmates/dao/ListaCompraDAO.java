package edu.eetac.dsa.flatmates.dao;

import edu.eetac.dsa.flatmates.entity.ColeccionListaCompra;
import edu.eetac.dsa.flatmates.entity.ListaCompra;

import java.sql.SQLException;

/**
 * Created by Admin on 24/11/2015.
 */
public interface ListaCompraDAO {
    public ListaCompra createLista(String item, String grupoid) throws SQLException;
    public ListaCompra getListaById(String id) throws SQLException;
    public ColeccionListaCompra getLista(String grupoid) throws SQLException;
    public ListaCompra updateLista(String id, String item) throws SQLException;
    public boolean deleteLista(String id) throws SQLException;
}
