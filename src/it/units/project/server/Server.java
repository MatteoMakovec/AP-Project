package it.units.project.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server implements CommandProcessor {
    protected final int port;
    protected final String quitCommand;
    protected double[] stats;

    public Server(int port, String quitCommand) {
        this.port = port;
        this.quitCommand = quitCommand;
        stats = new double[3];
    }

    public void start () throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    System.err.println("["+new Date()+"] New connection from client");
                    ClientHandler clientHandler = new ClientHandler(socket, quitCommand, this);
                    clientHandler.start();
                } catch (IOException e) {
                    System.err.printf("["+new Date()+"] Cannot accept connection due to %s", e);
                }
            }
        }
    }

    public String process(String input) {
        return input;
    }

    public String getQuitCommand() {
        return quitCommand;
    }
}
