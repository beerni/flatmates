package edu.eetac.dsa.flatmates;

import edu.eetac.dsa.flatmates.dao.AuthTokenDAOImpl;
import edu.eetac.dsa.flatmates.dao.UserAlreadyExistsException;
import edu.eetac.dsa.flatmates.dao.UserDAO;
import edu.eetac.dsa.flatmates.dao.UserDAOImpl;
import edu.eetac.dsa.flatmates.entity.AuthToken;
import edu.eetac.dsa.flatmates.entity.User;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.UUID;
import java.lang.String;

/**
 * Created by Admin on 22/11/2015.
 */

@Path("users")
public class UserResource {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(FlatmatesMediaType.FLATMATES_AUTH_TOKEN)
    public Response registerUser(@FormParam("login") String loginid, @FormParam("password") String password, @FormParam("email") String email, @FormParam("fullname") String fullname, @FormParam("info") String info, @FormParam("sexo") boolean sexo, @Context UriInfo uriInfo) throws URISyntaxException {
        if(loginid == null || password == null || email == null || fullname == null)
            throw new BadRequestException("all parameters are mandatory");
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        AuthToken authenticationToken = null;
        try{
            user = userDAO.createUser(loginid, password, email, fullname, info, sexo);
            authenticationToken = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        }catch (UserAlreadyExistsException e){
            throw new WebApplicationException("loginid already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + user.getId());
        return Response.created(uri).type(FlatmatesMediaType.FLATMATES_AUTH_TOKEN).entity(authenticationToken).build();
    }
    @Path("/{id}")
    @GET
    @Produces(FlatmatesMediaType.FLATMATES_USER)
    public User getUser(@PathParam("id") String id) {
        User user = null;
        try {
            user = (new UserDAOImpl()).getUserById(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(user == null)
            throw new NotFoundException("User with id = "+id+" doesn't exist");
        return user;
    }
    @Context
    private SecurityContext securityContext;

    @Path("/{id}")
    @PUT
    @Consumes(FlatmatesMediaType.FLATMATES_USER)
    @Produces(FlatmatesMediaType.FLATMATES_USER)
    public User updateUser(@PathParam("id") String id, User user) {
        if(user == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(user.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(id))
            throw new ForbiddenException("operation not allowed");

        UserDAO userDAO = new UserDAOImpl();
        try {
            user = userDAO.updateProfile(userid, user.getEmail(), user.getFullname(), user.getInfo());
            if(user == null)
                throw new NotFoundException("User with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return user;
    }
    @Path("/{id}")
    @DELETE
    public void deleteUser(@PathParam("id") String id){
        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(id))
            throw new ForbiddenException("operation not allowed");
        UserDAO userDAO = new UserDAOImpl();
        try {
            if(!userDAO.deleteUser(id))
                throw new NotFoundException("User with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}
