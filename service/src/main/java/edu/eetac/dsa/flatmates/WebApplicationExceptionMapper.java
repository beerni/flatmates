package edu.eetac.dsa.flatmates;

import edu.eetac.dsa.flatmates.entity.FlatmatesError;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by bernat on 9/12/15.
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> { //mapear excepciones a respuestas específicas
    @Override
    public Response toResponse(WebApplicationException e) { //crea una nueva entidad BeeterError que incluye en la respuesta HTTP con estado de error.
        FlatmatesError error = new FlatmatesError(e.getResponse().getStatus(), e.getMessage());
        return Response.status(error.getStatus()).entity(error).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}