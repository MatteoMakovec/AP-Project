package it.units.project.server;

import it.units.project.request.InputScanner;

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
                InputScanner inputScanner = new InputScanner(line, server.getQuitCommand());

                // here we have to implement the right code to parse the input from the client

                bw.write(server.process(line) + System.lineSeparator());
                bw.flush();
            }
        } catch (IOException e) {
            System.err.printf("IO error: %s\n", e);
        }
    }
}