package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;
import edu.school21.sockets.client.MockTankController;
import edu.school21.sockets.client.TankController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--server-port=")){
            System.err.println("Enter port using --server-port=?");
            System.exit(1);
        }
        TankController tankController = new MockTankController();
        try {
            int port = Integer.parseInt(args[0].substring(14));
            Client client = new Client("127.0.0.1", port, tankController);
            client.start();
        } catch (RuntimeException | IOException e) {
            System.out.println(e);
        }

    }
}
