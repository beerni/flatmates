package edu.eetac.dsa.flatmates.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.eetac.dsa.flatmates.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Admin on 08/11/2015.
 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class AuthToken {
    @InjectLinks({
            @InjectLink(resource = FlatmatesRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Flatmates Root Api"),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "self login", title = "Login", type = FlatmatesMediaType.FLATMATES_AUTH_TOKEN),
            @InjectLink(resource = MensajeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-messages", title = "Current messages", type = FlatmatesMediaType.FLATMATES_MENSAJE_COLLECTION),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = MensajeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-message", title = "Create message", type = FlatmatesMediaType.FLATMATES_MENSAJE),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", type = FlatmatesMediaType.FLATMATES_USER, bindings = @Binding(name = "id", value = "${instance.userid}"))

                })

        private List<Link> links;

        private String userid;
        private String token;

        public List<Link> getLinks() {
            return links;
        }

        public void setLinks(List<Link> links) {
            this.links = links;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


