package edu.school21.sockets.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClient extends Thread {
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;

    public ServerClient(Socket socket) throws IOException {
        this.socket = socket;

        reader = new ObjectInputStream(socket.getInputStream());
        writer = new ObjectOutputStream(socket.getOutputStream());

        start();
    }

    @Override
    public void run() {
        while (true) {
            Command command = getUserCommand();

            for (ServerClient client : Server.clients){
                if (client == this) continue;
                client.sendUserCommand(command);
            }
        }
    }

    private Command getUserCommand() {
        try {
            return (Command) reader.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendUserCommand(Command command) {
        try {
            writer.writeObject(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
