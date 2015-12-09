package edu.eetac.dsa.flatmates;

import edu.eetac.dsa.flatmates.dao.MensajeDAO;
import edu.eetac.dsa.flatmates.dao.MensajeDAOImpl;
import edu.eetac.dsa.flatmates.entity.AuthToken;
import edu.eetac.dsa.flatmates.entity.ColeccionMensaje;
import edu.eetac.dsa.flatmates.entity.Grupo;
import edu.eetac.dsa.flatmates.entity.Mensaje;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Admin on 23/11/2015.
 */
@Path("/mensaje")
public class MensajeResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(FlatmatesMediaType.FLATMATES_MENSAJE)
    public Response createMensaje(@FormParam("subject") String subject, @FormParam("Mensaje") String mensaje, @Context UriInfo uriInfo) throws URISyntaxException {
        if (subject == null || mensaje == null)
            throw new BadRequestException("All parameters are mandatory");
        String userid = securityContext.getUserPrincipal().getName();
        MensajeDAO mensajeDAO = new MensajeDAOImpl();
        Mensaje mensajes = null;
        try {
            mensajes = mensajeDAO.createMensaje(userid, subject, mensaje);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + mensajes.getId());
        return Response.created(uri).type(FlatmatesMediaType.FLATMATES_MENSAJE).entity(mensajes).build();
    }
    @GET
    @Produces(FlatmatesMediaType.FLATMATES_MENSAJE_COLLECTION)
    public ColeccionMensaje getMensajes(){
        ColeccionMensaje mensajesCollection = null;
        MensajeDAO mensajeDAO = new MensajeDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        try {
            mensajesCollection = mensajeDAO.getMensaje();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return mensajesCollection;
    }
    @Path("/{id}")
    @GET
    @Produces(FlatmatesMediaType.FLATMATES_MENSAJE)
    public Mensaje getMensaje(@PathParam("id") String id){
        Mensaje mensaje = null;
        MensajeDAO mensajeDAO = new MensajeDAOImpl();
        try {
            mensaje = mensajeDAO.getMensajeById(id);
            if(mensaje == null)
                throw new NotFoundException("Mensaje with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return mensaje;
    }

    @Path("/{id}")
    @PUT
    @Consumes(FlatmatesMediaType.FLATMATES_MENSAJE)
    @Produces(FlatmatesMediaType.FLATMATES_MENSAJE)
    public Mensaje updateMensaje(@PathParam("id") String id, Mensaje mensaje) {
        if(mensaje == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(mensaje.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(mensaje.getUserid()))
            throw new ForbiddenException("operation not allowed");

        MensajeDAO mensajeDAO = new MensajeDAOImpl();
        try {
            mensaje = mensajeDAO.updateMensaje(id, mensaje.getSubject(), mensaje.getMensaje());
            if(mensaje == null)
                throw new NotFoundException("Mensaje with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return mensaje;
    }
    @Path("/{id}")
    @DELETE
    public void deleteMensaje(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        MensajeDAO mensajeDAO = new MensajeDAOImpl();
        try {
            String ownerid = mensajeDAO.getMensajeById(id).getUserid();
            if(!userid.equals(ownerid)&&!securityContext.isUserInRole("admin"))
                throw new ForbiddenException("operation not allowed");
            if(!mensajeDAO.deleteMensaje(id))
                throw new NotFoundException("Mensaje with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }


}

