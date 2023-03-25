package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter writer;
    private Scanner reader;

    public Client(String ip, int port) {

        try {
            socket = new Socket(ip, port);
            reader = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    synchronized public void start() throws IOException {
        System.out.println("Connecting to server...");
        ReadMsg readMsg = new ReadMsg();
        WriteMsg writeMsg = new WriteMsg();
        readMsg.start();
        writeMsg.start();
    }

    private void stop() {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        System.exit(0);
    }


    private class ReadMsg extends Thread {
        @Override
        public void run() {
            while (true) {
                String input = reader.nextLine();
                System.out.println("From server + " + input);
            }
        }
    }

    public class WriteMsg extends Thread {
        @Override
        public void run() {
            Scanner sc = new Scanner(System.in);
            while (true) {
                writer.println(sc.nextLine());
            }
        }
    }
}

