package edu.eetac.dsa.flatmates.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 28/11/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelacionPuntosTareas {
    @InjectLinks({})
    private List<Link> links;
    private String userid;
    private String idtarea;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIdtarea() {
        return idtarea;
    }

    public void setIdtarea(String idtarea) {
        this.idtarea = idtarea;
    }
}
