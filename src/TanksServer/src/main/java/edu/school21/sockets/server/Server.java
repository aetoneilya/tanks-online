package edu.school21.sockets.server;

import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
public class Server {
    private ServerSocket serverSocket;
    static List<ServerClient> clients = new ArrayList<>();


    public void start(int port) {

        try {
            while (true) {
                serverSocket = new ServerSocket(port);
                Socket socket = serverSocket.accept();

                if (clients.size() <= 2) {
                    System.out.println("Adding client");
                    ServerClient serverClient = new ServerClient(socket);
                    clients.add(serverClient);
                    serverClient.start();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
