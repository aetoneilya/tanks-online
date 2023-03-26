package edu.school21.server.services;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ServerClient extends Thread {
    private final Socket socket;
    private final PrintWriter writer;
    private final Scanner reader;

    public ServerClient(Socket socket) throws IOException {
        this.socket = socket;

        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        System.out.println("Start listening new client " + socket.getInetAddress() + " " + socket.getPort());


        while (true) {
            if (socket.isClosed()) break;

            String input = "";
            if (reader.hasNextLine()) {
                input = reader.nextLine();
            }

            if (input.equals("")) {
                System.out.println("disconnected");
                down();
                Server.clients.remove(this);
                break;
            }

            System.out.println("From user: " + input);

            for (ServerClient client : Server.clients) {
                if (client == this) continue;
                client.writer.println(input);
            }
        }

    }


    private void down() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                reader.close();
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
