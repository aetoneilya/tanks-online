package edu.school21.server;

import edu.school21.server.configurations.ServerConfiguration;
import edu.school21.server.services.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ServerConfiguration.class);
        Server server = applicationContext.getBean(Server.class);
        server.start();
    }

}