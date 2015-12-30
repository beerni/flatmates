package edu.eetac.dsa.flatmates;




import edu.eetac.dsa.flatmates.dao.*;
import edu.eetac.dsa.flatmates.entity.*;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.imageio.ImageIO;
import javax.ws.rs.*;

import javax.ws.rs.core.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.UUID;

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
            if (grupo == null)
                throw new NotFoundException("Group with id=" + id + " not found");
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
        Grupo grupo = null;
        try {
            grupo = grupoDAO.getGrupoById(id);
            if (grupo == null)
                throw new NotFoundException("Group with id=" + id + " not found");
            grupoUsuario = grupoDAO.getGrupoUserById(id, userid);
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
    public tareas getTarea(@PathParam("id") String id, @PathParam("idt") String idt){
        tareas Tareas = null;
        TareasDAO tareasDAO = new TareasDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        GrupoUsuario grupoUsuario = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;
        try {
            grupo = grupoDAO.getGrupoById(id);
            if (grupo == null)
                throw new NotFoundException("Group with id=" + id + " not found");
            grupoUsuario = grupoDAO.getGrupoUserById(id, userid);
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
    @Path("/{id}/grupo/{idg}/add")
    @POST
    @Produces(FlatmatesMediaType.FLATMATES_TAREA)
    public void selectTarea(@PathParam("id") String id, @PathParam("idg") String idg, @Context UriInfo uriInfo)  {

        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;
        TareasDAO tareasDAO = new TareasDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        GrupoUsuario grupoUsuario = null;
        tareas Tareas = null;
        try{
            grupo = grupoDAO.getGrupoById(idg);
            if (grupo == null)
                throw new NotFoundException("Group with id=" + idg+ " not found");
            grupoUsuario = grupoDAO.getGrupoUserById(idg, userid);
            Tareas = tareasDAO.getTareadById(id, idg);
            if (Tareas == null)
                throw new NotFoundException("Tarea with id=" + id + " not found");
            if(!grupoUsuario.getUserid().equals(userid))
                throw new ForbiddenException("You aren't the admin");
            if(Tareas.getUserid()!= null)
            {
                throw new ForbiddenException("Tarea with id=" + id + " have an user");
            }
            tareasDAO.selectTarea(idg, id, userid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}/grupo/{idg}/points/{points}")
    @POST
    public void puntuarTarea(@PathParam("id") String id, @PathParam("idg") String idg, @PathParam("points") int points)  {

        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;
        TareasDAO tareasDAO = new TareasDAOImpl();
        String userid = securityContext.getUserPrincipal().getName();
        GrupoUsuario grupoUsuario = null;
        tareas Tareas = null;
        RelacionPuntosTareas relacionPuntosTareas = null;
        try{
            System.out.println("1");
            if (points <0 || points > 10)
                throw new ForbiddenException("Use a puntuation betwen 0 and 10");
            grupo = grupoDAO.getGrupoById(idg);
            if (grupo == null)
                throw new NotFoundException("Group with id=" + idg+ " not found");
            grupoUsuario = grupoDAO.getGrupoUserById(idg, userid);
            Tareas = tareasDAO.getTareadById(id, idg);
            if (Tareas == null)
                throw new NotFoundException("Tarea with id=" + id + " not found");

            if(!grupoUsuario.getUserid().equals(userid))
              throw new ForbiddenException("You aren't the admin");

            if(Tareas.getUserid()== null)
                throw new ForbiddenException("Tarea with id=" + id + " doesn't have user assigned");

            if(Tareas.getUserid().equals(userid))
                throw new ForbiddenException("You can't point your task");
            if(!Tareas.isHecho())
                throw new ForbiddenException("This task doesn't be done");
            relacionPuntosTareas = tareasDAO.getRelation(userid, id);

            if (relacionPuntosTareas !=null)
                throw  new ForbiddenException("You have pointed this tarea");
            tareasDAO.pointsTarea(idg, id, userid, points);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
    @Path("/{idt}/grupo/{id}")
    @DELETE
    public void deleteTarea(@PathParam("id") String idg, @PathParam("idt") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        TareasDAO tareasDAO = new TareasDAOImpl();
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;
        tareas Tareas = null;
        try {
            grupo = grupoDAO.getGrupoById(idg);
            if (grupo == null)
                throw new NotFoundException("Group with id=" + idg + " not found");
            Tareas = tareasDAO.getTareadById(id, idg);
            if (Tareas == null)
                throw new NotFoundException("Tarea with id=" + id + " not found");
            if(!grupo.getAdmin().equals(userid))
                throw new ForbiddenException("operation not allowed");
            if(!tareasDAO.deleteTarea(idg, id))
                throw new NotFoundException("Tarea with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
    @Path("{id}/grupo/{grupoid}")
    @PUT
    @Consumes (MediaType.MULTIPART_FORM_DATA)
    @Produces(FlatmatesMediaType.FLATMATES_TAREA)
    public tareas updateTarea(@FormDataParam("imagen") InputStream imagen,
                                 @FormDataParam("imagen") FormDataContentDisposition fileDetail,
                                 @PathParam("id") String id, @PathParam("grupoid") String idg, @Context UriInfo uriInfo) throws URISyntaxException {

        TareasDAO tareasDAO = new TareasDAOImpl();
        Grupo grupo = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        tareas Tareas = null;
        String userid = securityContext.getUserPrincipal().getName();
        AuthToken authenticationToken = null;
        try{
            grupo = grupoDAO.getGrupoById(idg);
            if(grupo == null)
                throw new NotFoundException("Group with id = " + idg +" not found");
            Tareas = tareasDAO.getTareadById(id, idg);
            if(Tareas==null)
                throw new NotFoundException("Tarea with id = " + id + " not found");
            if(Tareas.getUserid() == null)
                throw new ForbiddenException("Only the user that have this tarea can modify");
            if(!Tareas.getUserid().equals(userid))
                throw new ForbiddenException("Only the user that have this tarea can modify");
            //UUID uuid = writeAndConvertImage(imagen);
            Tareas = tareasDAO.updateTarea(id, idg, imagen, userid);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }catch(NullPointerException e) {
            System.out.println(e.toString());
            throw new InternalServerErrorException();
        }
        //Tareas.setImageURL(app.getProperties().get("imgBaseURL") + Tareas.getImage();
        //URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + Tareas.getId());
        //return Response.created(uri).type(FlatmatesMediaType.FLATMATES_TAREA).entity(authenticationToken).build();
        return Tareas;
    }

    private UUID writeAndConvertImage(InputStream file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);

        } catch (IOException e) {
            throw new InternalServerErrorException(
                    "Something has been wrong when reading the file.");
        }
        UUID uuid = UUID.randomUUID();
        String filename = uuid.toString() + ".png";

        try {
            ImageIO.write(image, "png",

                    new File("/Users/Admin/flatmates-develop/imagen/" + filename));
        } catch (IOException e) {
            throw new InternalServerErrorException(
                    "Something has been wrong when converting the file.");
        }

        return uuid;
    }
}