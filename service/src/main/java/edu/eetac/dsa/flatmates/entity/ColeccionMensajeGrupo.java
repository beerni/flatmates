package edu.eetac.dsa.flatmates.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 08/11/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColeccionMensajeGrupo {
    @InjectLinks({


    })
    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<MensajeGrupo> mensajesgrupo = new ArrayList<>();
}
