package edu.eetac.dsa.flatmates;




import edu.eetac.dsa.flatmates.dao.*;
import edu.eetac.dsa.flatmates.entity.*;

import javax.ws.rs.*;

import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Admin on 06/12/2015.
 */
@Path("tarea")
public class TareaResource{
        @Context
        private SecurityContext securityContext;

    @Path("/grupo/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(FlatmatesMediaType.FLATMATES_TAREA)
    public Response añadirTarea(@PathParam("id") String id, @FormParam("tarea") String tarea, @Context UriInfo uriInfo) throws URISyntaxException {
        if (tarea == null)
            throw new BadRequestException("All parameters are mandatory");
        TareasDAO tareaDAO = new TareasDAOImpl();
        tareas Tareas = null;
        String userid = securityContext.getUserPrincipal().getName();
        Grupo grupo = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try {
            grupo = grupoDAO.getGrupoById(id);
            if(!grupo.getAdmin().equals(userid))
                throw new ForbiddenException("You aren't the admin");
            Tareas = tareaDAO.createTareas(id, tarea);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + Tareas.getId());
        return Response.created(uri).type(FlatmatesMediaType.FLATMATES_MENSAJE).entity(Tareas).build();
    }
    @Path("/grupo/{id}")
    @GET
    @Produces(FlatmatesMediaType.FLATMATES_LISTA_TAREA)
    public ColeccionTareas getTareas(@PathParam("id") String id){
        ColeccionTareas coleccionTareas = null;
        TareasDAO tareasDAO = new TareasDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        GrupoUsuario grupoUsuario = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try {
            grupoUsuario = grupoDAO.getGrupoUserById(id);
            if(!grupoUsuario.getUserid().equals(userid))
                throw new ForbiddenException("operation not allowed");
            coleccionTareas =  tareasDAO.getTareas(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return coleccionTareas;
    }
    @Path("/{idt}/grupo/{id}")
    @GET
    @Produces(FlatmatesMediaType.FLATMATES_TAREA)
    public tareas getLista(@PathParam("id") String id, @PathParam("idt") String idt){
        tareas Tareas = null;
        TareasDAO tareasDAO = new TareasDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        GrupoUsuario grupoUsuario = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try {
            grupoUsuario = grupoDAO.getGrupoUserById(id);
            if(!grupoUsuario.getUserid().equals(userid))
                throw new ForbiddenException("operation not allowed");
            Tareas = tareasDAO.getTareadById(idt, id);
            if(Tareas == null)
                throw new NotFoundException("Tarea with id = "+idt+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return Tareas;
    }
}