package edu.school21.server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Server {

    private final Integer serverPort;

    private ServerSocket serverSocket;

    static List<ServerClient> clients = new ArrayList<>();

    public void start() {
        System.out.println(serverPort);
        try {
            serverSocket = new ServerSocket(serverPort);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client wants to connect");

                if (clients.size() < 2) {
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
