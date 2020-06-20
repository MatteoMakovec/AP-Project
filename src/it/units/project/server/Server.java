package it.units.project.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server implements CommandProcessor{
    private final int port;
    private final String quitCommand;

    public Server(int port, String quitCommand) {
        this.port = port;
        this.quitCommand = quitCommand;
    }

    public void start () throws IOException{
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(socket, this);
                    clientHandler.start();
                } catch (IOException e) {
                    System.err.printf("["+new Date()+"] Cannot accept connection due to %s", e);
                }
            }
        }
    }

    public String process(String input) {   // TODO: potrebbe essere ottimizzato per il nostro programma
        return input;
    }

    public String getQuitCommand() {
        return quitCommand;
    }
}
