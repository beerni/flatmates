package edu.eetac.dsa.flatmates.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.eetac.dsa.flatmates.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 08/11/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Grupo {
    @InjectLinks({
            @InjectLink (resource = FlatmatesRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Flatmates Root API"),
            @InjectLink (resource = GrupoResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-grupo", title = "Current grupo"),
            @InjectLink (resource = GrupoResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-grupo", title = "Create grupo", type = FlatmatesMediaType.FLATMATES_GRUPO),
            @InjectLink (resource = GrupoResource.class, method = "getGrupo", style = InjectLink.Style.ABSOLUTE, rel = "self mensaje", title = "Grupo", type = FlatmatesMediaType.FLATMATES_GRUPO, bindings = @Binding(name = "id", value ="${instance.id}" )),
            @InjectLink (resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink (resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", type = FlatmatesMediaType.FLATMATES_USER, bindings = @Binding(name = "id", value = "${instance.admin}")),
    })
    private List<Link> links;
    private String id;
    private String nombre;
    private String userlogin;
    private String admin;
    private String info;
    private long creationTimestamp;
    private long lastModified;
    private List<GrupoUsuario> usuarios = new ArrayList<>();


    public List<GrupoUsuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<GrupoUsuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getUserlogin() {
        return userlogin;
    }

    public void setUserlogin(String user_login) {
        this.userlogin = user_login;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
