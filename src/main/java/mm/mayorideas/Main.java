package mm.mayorideas;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * With help from Nikolay Grozev.
 * Source: https://nikolaygrozev.wordpress.com/2014/10/16/rest-with-embedded-jetty-and-jersey-in-a-single-jar-step-by-step/
 */
public final class Main {

    private static final int PORT = 8015;

    public static void main(String[] args) {
        startServer();
    }

    private static void startServer() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(PORT);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/package to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "mm.mayorideas.api");
        jerseyServlet.setInitParameter(
                "com.sun.jersey.api.json.POJOMappingFeature", "true");

        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jettyServer.destroy();
        }
    }
}