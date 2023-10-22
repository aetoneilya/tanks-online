package edu.school21.server.services;

import edu.school21.server.models.RoundAnalytics;
import edu.school21.server.repositories.RoundAnalyticsImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ServerClient extends Thread {

    private final Socket socket;

    private final PrintWriter writer;

    private final Scanner reader;

    private RoundAnalyticsImpl roundAnalytics;

    public ServerClient(Socket socket, RoundAnalyticsImpl roundAnalytics) throws IOException {
        this.socket = socket;
        this.roundAnalytics = roundAnalytics;
        this.reader = new Scanner(socket.getInputStream());
        this.writer = new PrintWriter(socket.getOutputStream(), true);
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

            if (input.startsWith("shoot")) {
                Scanner scanner = new Scanner(input);
                scanner.next();
                Integer shootsCount = scanner.nextInt();
                Integer hitsCount = scanner.nextInt();
                if (roundAnalytics.findByPlayerId(socket.getInetAddress().toString()) == null) {
                    roundAnalytics.save(new RoundAnalytics(socket.getInetAddress().toString(), shootsCount, hitsCount));
                } else {
                    roundAnalytics.update(new RoundAnalytics(socket.getInetAddress().toString(), shootsCount, hitsCount));
                }
                scanner.close();
            }

            System.out.println("From user: " + input);

            for (ServerClient client : Server.clients) {
                if (input.startsWith("finish")) {
                    List<RoundAnalytics> roundAnalyticsList = roundAnalytics.findAll();
                    for (RoundAnalytics el : roundAnalyticsList) {
                        if (!socket.getInetAddress().toString().equals(el.getPlayerId())) {
                            client.writer.println("finish " + el.getShotsCount() + " " + el.getHitsCount());
                        }
                    }
                }
                if (client == this && !input.startsWith("ready")) continue;
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
