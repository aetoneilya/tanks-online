package edu.school21.server.services;

import edu.school21.server.repositories.RoundAnalyticsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:server.properties")
public class Server {

    private final Integer serverPort;

    private final ServerSocket serverSocket;

    static List<ServerClient> clients = new ArrayList<>();

    @Autowired
    private RoundAnalyticsImpl roundAnalytics;

    public Server(@Value("${server.port:9000}") Integer serverPort) throws IOException {
        this.serverPort = serverPort;
        this.serverSocket = new ServerSocket(serverPort);
    }

    public void start() {
        System.out.println(serverPort);
        roundAnalytics.deleteAll();
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client wants to connect");

                if (clients.size() < 2) {
                    System.out.println("Adding client");
                    ServerClient serverClient = new ServerClient(socket, roundAnalytics);
                    clients.add(serverClient);
                    serverClient.start();
                } else {
                    System.out.println("No more space in list");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
