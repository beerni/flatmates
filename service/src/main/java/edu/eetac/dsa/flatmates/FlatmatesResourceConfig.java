package edu.eetac.dsa.flatmates;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by Admin on 28/11/2015.
 */
public class FlatmatesResourceConfig extends ResourceConfig{
    public FlatmatesResourceConfig() {
        packages("edu.eetac.dsa.flatmates");
        packages("edu.eetac.dsa.flatmates.auth");
        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);

    }
}