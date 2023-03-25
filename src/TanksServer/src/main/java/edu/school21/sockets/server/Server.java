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
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client wants to connect");

                if (clients.size() <= 2) {
                    System.out.println("Adding client");
                    ServerClient serverClient = new ServerClient(socket);
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
