package it.units.project.server;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final Server server;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (socket ;
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ) {
            while (true) {
                String line = br.readLine();
                System.out.println("[log] '" + line + "'");
                if (line == null) {
                    System.err.println("Client abruptly closed connection");
                    break;
                }
                if (line.toUpperCase().equals(server.getQuitCommand())) {  // Un alternativa poteva essere contornare questo if con un try-catch e nel catch catturare un NullPointerException (che rappresenta il caso line == null dello statement if sovrastante)
                    break;
                }
                bw.write(server.process(line) + System.lineSeparator());
                bw.flush();
            }
        } catch (IOException e) {
            System.err.printf("IO error: %s\n", e);
        }
    }
}