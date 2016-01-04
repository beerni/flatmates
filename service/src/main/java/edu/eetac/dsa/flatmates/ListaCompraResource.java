package edu.eetac.dsa.flatmates;

import edu.eetac.dsa.flatmates.dao.GrupoDAO;
import edu.eetac.dsa.flatmates.dao.GrupoDAOImpl;
import edu.eetac.dsa.flatmates.dao.ListaCompraDAO;
import edu.eetac.dsa.flatmates.dao.ListaCompraDAOImpl;
import edu.eetac.dsa.flatmates.entity.ColeccionListaCompra;
import edu.eetac.dsa.flatmates.entity.Grupo;
import edu.eetac.dsa.flatmates.entity.GrupoUsuario;
import edu.eetac.dsa.flatmates.entity.ListaCompra;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 24/11/2015.
 */
@Path("/lista")
public class ListaCompraResource {
    @Context
    private SecurityContext securityContext;

    @Path("/grupo/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(FlatmatesMediaType.FLATMATES_LISTA)
    public Response añadirItem(@PathParam("id") String id, @FormParam("item") String item, @Context UriInfo uriInfo) throws URISyntaxException {
        if (item == null)
            throw new BadRequestException("All parameters are mandatory");
        ListaCompraDAO listaCompraDAO = new ListaCompraDAOImpl();
        ListaCompra listaCompra = null;
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
            listaCompra = listaCompraDAO.createLista(item, id);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + listaCompra.getId());
        return Response.created(uri).type(FlatmatesMediaType.FLATMATES_MENSAJE).entity(listaCompra).build();
    }
    @Path("/grupo/{id}")
    @GET
    @Produces(FlatmatesMediaType.FLATMATES_LISTA_COLLECTION)
    public ColeccionListaCompra getListas(@PathParam("id") String id){
        ColeccionListaCompra listaCompraCollection = null;
        ListaCompraDAO listaCompraDAO = new ListaCompraDAOImpl();
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
            listaCompraCollection = listaCompraDAO.getLista(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }

        return listaCompraCollection;
    }
    @Path("/{idlista}/grupo/{id}")
    @GET
    @Produces(FlatmatesMediaType.FLATMATES_LISTA)
    public ListaCompra getLista(@PathParam("id") String id, @PathParam("idlista") String idlista){
        ListaCompra listaCompra = null;
        ListaCompraDAO listaCompraDAO = new ListaCompraDAOImpl();
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
            listaCompra = listaCompraDAO.getListaById(idlista);
            if(listaCompra == null)
                throw new NotFoundException("Lista with id = "+idlista+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return listaCompra;
    }

    @Path("/{idt}/grupo/{id}")
    @PUT
    @Consumes(FlatmatesMediaType.FLATMATES_LISTA)
    @Produces(FlatmatesMediaType.FLATMATES_LISTA)
    public ListaCompra updateLista(@PathParam("id") String id, @PathParam("idt") String idt, ListaCompra listaCompra) {
        if(listaCompra.getId() == null||listaCompra.getItem()==null||listaCompra.getGrupoid()==null)
            throw new BadRequestException("entity is null");
        if(!idt.equals(listaCompra.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");
        String userid = securityContext.getUserPrincipal().getName();
        GrupoUsuario grupoUsuario = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;
        ListaCompraDAO listaCompraDAO = new ListaCompraDAOImpl();
        try {
            grupo = grupoDAO.getGrupoById(id);
            if (grupo == null)
                throw new NotFoundException("Group with id=" + id + " not found");
            grupoUsuario = grupoDAO.getGrupoUserById(id, userid);
            if(!grupoUsuario.getUserid().equals(userid))
                throw new ForbiddenException("operation not allowed");
            listaCompra = listaCompraDAO.updateLista(listaCompra.getId(), listaCompra.getItem(), listaCompra.getGrupoid());
            if(listaCompra == null)
                throw new NotFoundException("Item with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return listaCompra;
    }

    @Path("/{idt}/grupo/{id}")
    @DELETE
    public void deleteLista(@PathParam("id") String idg, @PathParam("idt") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        ListaCompraDAO listaCompraDAO = new ListaCompraDAOImpl();
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        GrupoUsuario grupoUsuario = null;
        Grupo grupo = null;
        ListaCompra listaCompra = null;
        try {
            grupo = grupoDAO.getGrupoById(idg);
            if (grupo == null)
                throw new NotFoundException("Group with id=" + idg + " not found");
            listaCompra = listaCompraDAO.getListaById(id);
            if (listaCompra == null)
                throw new NotFoundException("Item with id=" + id + " not found");
            grupoUsuario = grupoDAO.getGrupoUserById(idg, userid);
            if(!grupoUsuario.getUserid().equals(userid))
                throw new ForbiddenException("operation not allowed");
            if(!listaCompraDAO.deleteLista(id))
                throw new NotFoundException("Item with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
    @Path("/{id}/grupo/{idgrupo}/hecho")
    @POST
    @Produces(FlatmatesMediaType.FLATMATES_LISTA)
    public void hecho(@PathParam("id") String id, @PathParam("idgrupo") String idg)  {
        String userid = securityContext.getUserPrincipal().getName();
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;
        ListaCompra listaCompra = null;
        GrupoUsuario grupoUsuario = null;
        ListaCompraDAO listaCompraDAO = new ListaCompraDAOImpl();
        try{
            grupo = grupoDAO.getGrupoById(idg);
            if (grupo == null)
            {
                throw new NotFoundException("Group with id=" + idg + " not found");
            }
            grupoUsuario = grupoDAO.getGrupoUserById(idg, userid);
            if(!grupoUsuario.getUserid().equals(userid))
                throw new ForbiddenException("You aren't the admin");
            listaCompra = listaCompraDAO.getListaById(id);
            if (listaCompra == null)
            {
                throw new NotFoundException("Item with id = "+id+" doesn't exist");
            }
            if (listaCompra.isHecho())
                throw new ForbiddenException("This item is did");
            listaCompraDAO.HechoLista(id, idg);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}
