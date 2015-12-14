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
    public class Mensaje {
        @InjectLinks({
                @InjectLink (resource = FlatmatesRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Flatmates Root API"),
                @InjectLink (resource = MensajeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-messages", title = "Current messages"),
                @InjectLink (resource = MensajeResource.class, style = InjectLink.Style.ABSOLUTE, rel = "creates-message", title = "Create message", type = FlatmatesMediaType.FLATMATES_MENSAJE),
                @InjectLink (resource = MensajeResource.class, method = "getMensaje", style = InjectLink.Style.ABSOLUTE, rel = "self mensaje", title = "Mensaje", type = FlatmatesMediaType.FLATMATES_MENSAJE, bindings = @Binding(name = "id", value ="${instance.id}" )),
                @InjectLink (resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
                @InjectLink (resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", type = FlatmatesMediaType.FLATMATES_USER, bindings = @Binding(name = "id", value = "${instance.userid}")),
                @InjectLink (resource = MensajeResource.class, method = "getMensajes", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newest messages", type = FlatmatesMediaType.FLATMATES_MENSAJE_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "false")}),
                @InjectLink (resource = MensajeResource.class, method = "getMensajes", style = InjectLink.Style.ABSOLUTE, rel = "prev", title = "Oldest messages",type = FlatmatesMediaType.FLATMATES_MENSAJE_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "true")})
        })

        private List<Link> links;
        private String id;
        private String content;
        private String loginid;
        private String userid;
        private long creationTimestamp;
        private long lastModified;

        public String getLoginid() {
            return loginid;
        }

        public void setLoginid(String loginid) {
            this.loginid = loginid;
        }

        public List<Link> getLinks() {
            return links;
        }

        public void setLinks(List<Link> links) {
            this.links = links;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public long getCreationTimestamp() {
            return creationTimestamp;
        }

        public void setCreationTimestamp(long creationTimestamp) {
            this.creationTimestamp = creationTimestamp;
        }

        public long getLastModified() {
            return lastModified;
        }

        public void setLastModified(long lastModified) {
            this.lastModified = lastModified;
        }
    }


