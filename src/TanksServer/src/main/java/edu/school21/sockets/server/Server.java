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
            Socket socket = serverSocket.accept();

            if (clients.size() <= 2)
                clients.add(new ServerClient(socket));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
