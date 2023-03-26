package edu.school21.server.configurations;

import edu.school21.server.services.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:server.properties")
public class ServerConfiguration {

    @Value("${server.port:9000}")
    private Integer serverPort;

    @Bean
    public Server getServer() {
        return new Server(serverPort);
    }

}
