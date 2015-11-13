package edu.eetac.dsa.flatmates.entity;

import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by Admin on 08/11/2015.
 */
public class FlatmatesRootAPI {
        @InjectLinks({
               })
        private List<Link> links;

        public List<Link> getLinks() {
            return links;
        }

        public void setLinks(List<Link> links) {
            this.links = links;
        }
}
