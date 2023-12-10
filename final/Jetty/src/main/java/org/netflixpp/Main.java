package org.netflixpp;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class Main {

    public static final String BASE_URI = "http://192.168.0.127:8080/";

    public static Server startServer() {
        //final ResourceConfig config = new ResourceConfig().packages("com.netflixpp");
        final ResourceConfig config = new ResourceConfig(ConnectionDB.class, AccessMoviesDB.class, AccessUser.class);
        return JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);

    }

    public static void main(String[] args) {
            try {

                final Server server = startServer();
                //final Server server= new Server(8080);

                try {
                    server.start();
                    server.join();
                } catch (Exception e) {
                    System.exit(1);
                }
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        System.out.println("EXITING THE APLICATION...");
                        server.stop();
                        System.out.println("Exit completed.");
                    } catch (Exception e) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                    }
                }));

                System.out.printf("Sucessful :)%n Apliication Started.%nStop the application using CTRL+C%n");

                //shut down signal, CTRL+C
                Thread.currentThread().join();


            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
}