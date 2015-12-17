package edu.eetac.dsa.flatmates.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.eetac.dsa.flatmates.FlatmatesMediaType;
import edu.eetac.dsa.flatmates.FlatmatesRootAPIResource;
import edu.eetac.dsa.flatmates.LoginResource;
import edu.eetac.dsa.flatmates.MensajeResource;
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
public class ColeccionMensaje {
    @InjectLinks({
            @InjectLink (resource = FlatmatesRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Flatmates Root API"),
            @InjectLink (resource = MensajeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-messages", title = "Current messages",type = FlatmatesMediaType.FLATMATES_MENSAJE_COLLECTION),
            @InjectLink (resource = MensajeResource.class, method = "getMensajes", style = InjectLink.Style.ABSOLUTE, rel = "prev", title = "Newest messages", type = FlatmatesMediaType.FLATMATES_MENSAJE_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.oldestTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink (resource = MensajeResource.class, method = "getMensajes", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Oldest messages",type = FlatmatesMediaType.FLATMATES_MENSAJE_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.oldestTimestamp}"), @Binding(name = "before", value = "true")}),
            @InjectLink (resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout")
    })


    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Mensaje> mensajes = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }

    public long getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> Mensajes) {
        this.mensajes = Mensajes;
    }
}
