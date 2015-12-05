package edu.eetac.dsa.flatmates;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;

/**
 * Created by roc on 03/12/2015.
 */

@Path("/multipart")
public class ImageResource {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response processForm(@FormDataParam("file") InputStream uploadedInputStream,
                                @FormDataParam("file") FormDataContentDisposition fileDetail) {
        String output = "File uploaded to : " + fileDetail.getFileName();
        System.out.println(output);
        return Response.status(200).entity(output).build();
    }

}