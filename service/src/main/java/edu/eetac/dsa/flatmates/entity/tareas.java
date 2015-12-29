package edu.eetac.dsa.flatmates.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.eetac.dsa.flatmates.FlatmatesMediaType;
import edu.eetac.dsa.flatmates.TareaResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Admin on 08/11/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class tareas {
    @InjectLinks({
            @InjectLink(resource = TareaResource.class, method = "selectTarea", style = InjectLink.Style.ABSOLUTE, rel = "select", title = "Select", type = FlatmatesMediaType.FLATMATES_TAREA, bindings = {@Binding(name = "id", value ="${instance.id}"), @Binding(name = "idg", value="${instance.grupoid}")}),
            @InjectLink(resource = TareaResource.class, method = "deleteTarea", style = InjectLink.Style.ABSOLUTE, rel = "Delete", title = "Delete", type = FlatmatesMediaType.FLATMATES_TAREA, bindings = {@Binding(name = "idt", value ="${instance.id}"), @Binding(name = "id", value="${instance.grupoid}")})

    })
    private List<Link> links;
    private String id;
    private String userid;
    private String grupoid;
    private String tarea;
    private String image;
    private int puntos;
    private boolean hecho;

    public boolean isHecho() {
        return hecho;
    }

    public void setHecho(boolean hecho) {
        this.hecho = hecho;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public List<Link> getLinks() {
        return links;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGrupoid() {
        return grupoid;
    }

    public void setGrupoid(String grupoid) {
        this.grupoid = grupoid;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }
}
