package edu.eetac.dsa.flatmates.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 23/11/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColeccionListaCompra {
    @InjectLinks({})
    private List<Link> links;
    private List<ListaCompra> listaCompras = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<ListaCompra> getListaCompras() {
        return listaCompras;
    }

    public void setListaCompras(List<ListaCompra> listaCompras) {
        this.listaCompras = listaCompras;
    }
}
