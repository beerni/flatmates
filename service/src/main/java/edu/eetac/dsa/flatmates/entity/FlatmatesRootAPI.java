package edu.eetac.dsa.flatmates.entity;

import edu.eetac.dsa.flatmates.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Admin on 08/11/2015.
 */
public class FlatmatesRootAPI {
        @InjectLinks({@InjectLink(resource = FlatmatesRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self bookmark home", title = "Flatmates Root API")
                ,@InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "login", title = "Login",  type= FlatmatesMediaType.FLATMATES_AUTH_TOKEN)
                ,@InjectLink(resource = UserResource.class, style = InjectLink.Style.ABSOLUTE, rel = "user      ", title = "Register", type = FlatmatesMediaType.FLATMATES_AUTH_TOKEN)
                ,@InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout", condition = "${!empty resource.userid}")
                ,@InjectLink(resource = MensajeResource.class, style =InjectLink.Style.ABSOLUTE, rel = "current-messages", title = "Current messages", condition ="${!empty resource.userid}", type = FlatmatesMediaType.FLATMATES_MENSAJE_COLLECTION)
                ,@InjectLink(resource =MensajeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-message", title = "Create message", condition = "${!empty resource.userid}", type = FlatmatesMediaType.FLATMATES_MENSAJE)
                ,@InjectLink(resource =GrupoResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-grupo", title = "Create grupo", condition = "${!empty resource.userid}", type = FlatmatesMediaType.FLATMATES_GRUPO)
                ,@InjectLink(resource =GrupoResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-grupo", title = "Current grupo", condition = "${!empty resource.userid}", type = FlatmatesMediaType.FLATMATES_GRUPO)
                ,@InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", condition = "${!empty resource.userid}", type = FlatmatesMediaType.FLATMATES_USER, bindings = @Binding(name = "id", value = "${resource.userid}"))

               })
        private List<Link> links;

        public List<Link> getLinks() {
            return links;
        }

        public void setLinks(List<Link> links) {
            this.links = links;
        }
}
