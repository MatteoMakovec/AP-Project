package it.units.project.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements CommandProcessor {
    protected final int port;
    protected final String quitCommand;
    protected static ExecutorService executorService;

    public Server(int port, String quitCommand) {
        this.port = port;
        this.quitCommand = quitCommand;
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void start () throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    System.out.println("["+new Date()+"] New connection from client");
                    ClientHandler clientHandler = new ClientHandler(socket, quitCommand, this);
                    clientHandler.start();
                } catch (IOException e) {
                    System.err.printf("["+new Date()+"] Cannot accept connection due to %s", e);
                }
            }
        } finally {
            executorService.shutdown();
        }
    }

    public String process(String input) {
        return input;
    }

    public String getQuitCommand() {
        return quitCommand;
    }
}
