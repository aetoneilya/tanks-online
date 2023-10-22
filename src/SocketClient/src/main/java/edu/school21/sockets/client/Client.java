package edu.school21.sockets.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter writer;
    private Scanner reader;
    private final TankController tankController;

    public Client(String ip, int port, TankController tankController) {
        this.tankController = tankController;

        try {
            socket = new Socket(ip, port);
            reader = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    synchronized public void start() throws IOException {
        ReadMsg readMsg = new ReadMsg();
//        WriteMsg writeMsg = new WriteMsg();
        readMsg.start();
//        writeMsg.start();
    }

    private void stop() {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.exit(0);
    }


    private class ReadMsg extends Thread {
        @Override
        public void run() {
            while (true) {
                String input = reader.nextLine();
                if (input.startsWith("shoot")) {
                    tankController.shoot();
                } else if (input.startsWith("finish")) {
                    tankController.setEnemyStats(input);
                } else {

                    switch (input) {
                        case "left":
                            tankController.left();
                            break;
                        case "right":
                            tankController.right();
                            break;
                        case "giveup":
                            tankController.giveUp();
                            break;
                        case "ready":
                            tankController.readyToStart();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
//    public class WriteMsg extends Thread {
//        @Override
//        public void run() {
//            Scanner sc = new Scanner(System.in);
//            while (true) {
//                writer.println(sc.nextLine());
//            }
//        }
//    }
}

