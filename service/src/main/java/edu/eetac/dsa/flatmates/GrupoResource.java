package edu.eetac.dsa.flatmates;

import edu.eetac.dsa.flatmates.dao.GrupoDAO;
import edu.eetac.dsa.flatmates.dao.GrupoDAOImpl;
import edu.eetac.dsa.flatmates.entity.AuthToken;
import edu.eetac.dsa.flatmates.entity.ColeccionGrupo;
import edu.eetac.dsa.flatmates.entity.Grupo;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by Admin on 22/11/2015.
 */
@Path("grupo")
public class GrupoResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(FlatmatesMediaType.FLATMATES_GRUPO)
    public Response createGrupo(@FormParam("nombre") String nombre,@FormParam("info") String info, @Context UriInfo uriInfo) throws URISyntaxException {
        if (!securityContext.isUserInRole("admin"))
            throw new ForbiddenException("You are not allowed to create a group.");
        if(nombre== null)
            throw new BadRequestException("all parameters are mandatory");
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;
        AuthToken authenticationToken = null;
        try{
            grupo = grupoDAO.createGrupo(securityContext.getUserPrincipal().getName(),nombre, info);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + grupo.getId());
        return Response.created(uri).type(FlatmatesMediaType.FLATMATES_GRUPO).entity(grupo).build();
    }
    @Path("/{id}")
    @POST
    @Produces(FlatmatesMediaType.FLATMATES_GRUPO)
    public void eliminarusuarioGrupo(@PathParam("id") String id, @FormParam("userid") String userid, @Context UriInfo uriInfo)  {

        GrupoDAO grupoDAO = new GrupoDAOImpl();
        AuthToken authenticationToken = null;
        try{
            grupoDAO.eliminarusuarioGrupo(id, userid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @GET
    @Produces(FlatmatesMediaType.FLATMATES_GRUPO_COLLECTION)
    public ColeccionGrupo getGrupo(){
        ColeccionGrupo grupoCollection = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try {
            grupoCollection = grupoDAO.getGrupos();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return grupoCollection;
    }

    @Path("/{id}")
    @GET
    @Produces(FlatmatesMediaType.FLATMATES_GRUPO)
    public Grupo getGrupo(@PathParam("id") String id){
        Grupo grupo = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try {
            grupo = grupoDAO.getGrupoById(id);
            if(grupo == null)
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return grupo;
    }
    @Path("/{id}")
    @DELETE
    public void deleteGrupo(@PathParam("id") String id) {
        if (!securityContext.isUserInRole("admin"))
            throw new ForbiddenException("You are not allowed to delete a group.");
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try {

            if(!grupoDAO.deleteGrupo(id))
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
    @Path("/su={id}")
    @DELETE
    public void eliminaruserGrupo(@PathParam("id") String id, @FormParam("userid") String userid, @Context UriInfo uriInfo) throws URISyntaxException {

        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try{
            if(!grupoDAO.eliminarusuarioGrupo(id, userid))
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }


}