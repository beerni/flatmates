package edu.eetac.dsa.flatmates;

import edu.eetac.dsa.flatmates.dao.GrupoDAO;
import edu.eetac.dsa.flatmates.dao.GrupoDAOImpl;
import edu.eetac.dsa.flatmates.dao.UserDAO;
import edu.eetac.dsa.flatmates.dao.UserDAOImpl;
import edu.eetac.dsa.flatmates.entity.*;

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
        if (!securityContext.isUserInRole("registered"))
            throw new ForbiddenException("You are not allowed to create a group.");
        if(nombre== null)
            throw new BadRequestException("all parameters are mandatory");
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        String users = securityContext.getUserPrincipal().getName();
        Grupo grupo = null;
        GrupoUsuario grupoUsuario = null;
        AuthToken authenticationToken = null;
        try{
            grupoUsuario = grupoDAO.getUserGrupoById(users);
            if (grupoUsuario!=null)
            {
                throw new ForbiddenException("You aren't allowed for create group, because you have group");
            }
            grupo = grupoDAO.createGrupo(users,nombre, info);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + grupo.getId());
        return Response.created(uri).type(FlatmatesMediaType.FLATMATES_GRUPO).entity(grupo).build();
    }
    @Path("/{id}")
    @POST
    @Produces(FlatmatesMediaType.FLATMATES_GRUPO)
    public void addusuarioGrupo(@PathParam("id") String id, @FormParam("loginid") String loginid, @Context UriInfo uriInfo)  {

        GrupoDAO grupoDAO = new GrupoDAOImpl();
        AuthToken authenticationToken = null;
        Grupo grupo = null;
        User user = null;
        GrupoUsuario grupoUsuario = null;
        UserDAO userDAO = new UserDAOImpl();
        try{
            grupo = grupoDAO.getGrupoById(id);
            if (grupo == null)
            {
                throw new NotFoundException("Group with id=" + id + " not found");
            }
                if(!grupo.getAdmin().equals(securityContext.getUserPrincipal().getName()))
                    throw new ForbiddenException("You aren't the admin");
            user = userDAO.getUserByLoginid(loginid);
            if (user == null)
            {
                throw new NotFoundException("User with loginid = "+loginid+" doesn't exist");
            }
            grupoUsuario = grupoDAO.getUserGrupoById(user.getId());
            if (grupoUsuario!=null)
            {
                throw new ForbiddenException("User " + loginid + " have group");
            }
            grupoDAO.añadirusuariosalGrupo(id, user.getId());
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
    @Path("/{id}")
    @GET
    @Produces(FlatmatesMediaType.FLATMATES_GRUPO)
    public Grupo getGrupo(@PathParam("id") String id){
        Grupo grupo = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        GrupoUsuario grupoUsuario = null;
        try {
            grupo = grupoDAO.getGrupoById(id);
            if (grupo == null)
            {
                throw new NotFoundException("Group with id = " + id + " not found");
            }
            grupoUsuario = grupoDAO.getGrupoUserById(id, userid);
            if(!grupoUsuario.getUserid().equals(userid))
                throw new ForbiddenException("operation not allowed");
            grupo = grupoDAO.getGrupoById(id);
            if(grupo == null)
                throw new NotFoundException("Grupo with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return grupo;
    }

    @Path("/{id}")
    @DELETE
    public void deleteGrupo(@PathParam("id") String id) {
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;
        try {
            grupo = grupoDAO.getGrupoById(id);
            if (grupo == null)
                throw new NotFoundException("Group with id = " + id + " not found");
            if(!grupo.getAdmin().equals(securityContext.getUserPrincipal().getName())&&!securityContext.isUserInRole("admin"))
                throw new ForbiddenException("You aren't the admin or the admin in the group");
            if(!grupoDAO.deleteGrupo(id))
                throw new NotFoundException("Group with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
    @Path("/{id}/user/{userid}")
    @DELETE
    public void eliminaruserGrupo(@PathParam("id") String id, @PathParam("userid") String userid) {
        String user = securityContext.getUserPrincipal().getName();
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        GrupoUsuario grupoUsuario = null;
        Grupo grupo = null;
        User users = null;
        UserDAO userDAO = new UserDAOImpl();
        try{
            users = userDAO.getUserById(userid);
            if (users == null)
            {
                throw new NotFoundException("User with id = " + userid + " not found");
            }
            grupo = grupoDAO.getGrupoById(id);
            if (grupo == null)
            {
                throw new NotFoundException("Grupo with id = " + id + " not found");
            }
            if(!grupo.getAdmin().equals(user)&&!userid.equals(user))
                throw new ForbiddenException("Only the admin or the own user can delete");
            if(grupo.getAdmin().equals(userid))
            {
                if(!grupoDAO.deleteGrupo(id))
                    throw new NotFoundException("Group with id = "+id+" doesn't exist");
            }
            if(!grupoDAO.eliminarusuarioGrupo(id, userid))
                throw new NotFoundException("Grupo with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }


}