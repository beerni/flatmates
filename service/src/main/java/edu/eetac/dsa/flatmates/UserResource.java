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
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.*;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.PathParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;

/**
 * Created by Admin on 22/11/2015.
 */

@Path("users")
public class UserResource {
    @Context
    private Application app;
    @POST
    @Consumes (MediaType.MULTIPART_FORM_DATA)
    @Produces(FlatmatesMediaType.FLATMATES_AUTH_TOKEN)
    public Response registerUser(@FormDataParam("login") String loginid,
                                 @FormDataParam("password") String password,
                                 @FormDataParam("email") String email,
                                 @FormDataParam("fullname") String fullname,
                                 @FormDataParam("info") String info,
                                 @FormDataParam("sexo") boolean sexo,
                                 @FormDataParam("imagen") InputStream imagen,
                                 @FormDataParam("imagen") FormDataContentDisposition fileDetail,
                                 @Context UriInfo uriInfo) throws URISyntaxException {


        if(loginid == null || password == null || email == null || fullname == null || imagen==null)
            throw new BadRequestException("all parameters are mandatory");
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        AuthToken authenticationToken = null;
        try{
            UUID uuid = writeAndConvertImage(imagen);
            user = userDAO.createUser(loginid, password, email, fullname, info, sexo, uuid.toString());
            user.setFilename(uuid.toString() + ".png");
            //user.setImageURL(app.getProperties().get("imgBaseURL") + user.getFilename());
            authenticationToken = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        }catch (UserAlreadyExistsException e){
            throw new WebApplicationException("loginid already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();

        }catch(NullPointerException e) {
            System.out.println(e.toString());
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + user.getId());
        return Response.created(uri).type(FlatmatesMediaType.FLATMATES_AUTH_TOKEN).entity(authenticationToken).build();
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

                    new File("/home/bernat/dsa-projects/flatmates-project/www/images/" + filename));
        } catch (IOException e) {
            throw new InternalServerErrorException(
                    "Something has been wrong when converting the file.");
        }

        return uuid;
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