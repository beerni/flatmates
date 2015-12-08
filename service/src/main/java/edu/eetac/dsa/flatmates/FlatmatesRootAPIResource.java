package edu.eetac.dsa.flatmates;

import edu.eetac.dsa.flatmates.entity.FlatmatesRootAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by bernat on 8/12/15.
 */
@Path("/")
public class FlatmatesRootAPIResource {
    @Context
    private SecurityContext securityContext;
    private String userid;
    @GET
    @Produces(FlatmatesMediaType.FLATMATES_ROOT)
    public FlatmatesRootAPI getRootAPI(){
        if(securityContext.getUserPrincipal()!=null)
            userid=securityContext.getUserPrincipal().getName();

        FlatmatesRootAPI flatmatesRootAPI = new FlatmatesRootAPI();

        return flatmatesRootAPI;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
