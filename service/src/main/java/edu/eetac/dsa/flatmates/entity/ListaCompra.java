package edu.eetac.dsa.flatmates.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.eetac.dsa.flatmates.FlatmatesMediaType;
import edu.eetac.dsa.flatmates.ListaCompraResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Admin on 23/11/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListaCompra {
       @InjectLinks({
               @InjectLink(resource = ListaCompraResource.class, method = "hecho", style = InjectLink.Style.ABSOLUTE, rel = "self lista", title = "Lista", type = FlatmatesMediaType.FLATMATES_LISTA, bindings = {@Binding(name = "id", value ="${instance.id}"), @Binding(name = "idgrupo", value="${instance.grupoid}")}),
               @InjectLink(resource = ListaCompraResource.class, method = "deleteLista", style = InjectLink.Style.ABSOLUTE, rel = "delete", title = "Delete", type = FlatmatesMediaType.FLATMATES_LISTA, bindings = {@Binding(name = "idt", value ="${instance.id}"), @Binding(name = "id", value="${instance.grupoid}")})

       })
        private List<Link> links;
        private String id;
        private String grupoid;
        private String item;
        private boolean hecho;

    public List<Link> getLinks() {
        return links;
    }

    public boolean isHecho() {
        return hecho;
    }

    public void setHecho(boolean hecho) {
        this.hecho = hecho;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrupoid() {
        return grupoid;
    }

    public void setGrupoid(String grupoid) {
        this.grupoid = grupoid;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
