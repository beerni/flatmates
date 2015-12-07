package edu.eetac.dsa.flatmates.dao;

/**
 * Created by Admin on 24/11/2015.
 */
public interface ListaCompraDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_LISTA = "insert into listacompra (id, item, grupoid, hecho) values (UNHEX(?), ?, unhex(?), '0')";
    public final static String GET_LISTA_BY_ID = "select hex(l.id) as id, l.item, l.hecho, hex(l.grupoid) as grupoid, g.id from listacompra l, grupo g where l.id=unhex(?) and g.id = l.grupoid";
    public final static String GET_LISTA = "select hex(id) as id, item, hex(grupoid) as grupoid, hecho from listacompra where grupoid = unhex(?)";
    public final static String UPDATE_LISTA = "update listacompra set item=? where id=unhex(?) and grupoid = unhex(?)";
    public final static String DELETE_LISTA = "delete from listacompra where id=unhex(?)";
    public final static String HECHO = "update listacompra set hecho='1' where id = unhex(?) and grupoid = unhex(?)";
}
