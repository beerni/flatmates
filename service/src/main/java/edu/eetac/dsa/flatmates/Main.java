package edu.eetac.dsa.flatmates;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Main class.
 *
 */
public class Main {


    // Base URI the Grizzly HTTP server will listen on
    //Para cargar el fichero de configuracion, grouptalk.properties que es donde esta la pagina.
    private static String baseURI;
    public final static String getBaseURI() {
        if (baseURI == null)
        {
            PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("flatmates");
            baseURI = prb.getString("flatmates.context");
        }
        return baseURI;
    }


    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in edu.upc.eetac.dsa.grouptalk package
        final ResourceConfig rc = new FlatmatesResourceConfig();

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI

        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(getBaseURI()), rc);
        org.glassfish.grizzly.http.server.HttpHandler httpHandler = new StaticHttpHandler("./www/");
        httpServer.getServerConfiguration().addHttpHandler(httpHandler,"/");



        return httpServer;

         //return GrizzlyHttpServerFactory.createHttpServer(URI.create(getBaseURI()), rc);


    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();

    }
}


