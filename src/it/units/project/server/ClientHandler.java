package it.units.project.server;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;


public class ClientHandler extends Thread {
    private final Socket socket;
    private final CommandProcessor server;

    public ClientHandler(Socket socket, CommandProcessor server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (socket;
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ) {
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    System.err.println("["+new Date()+"] Client abruptly closed connection");
                    break;
                }
                if (line.equals(server.getQuitCommand())) {
                    socket.close();
                    System.err.println("["+new Date()+"] Client disconnected");
                    break;
                }
                String response = server.process(line);
                bw.write(response + System.lineSeparator());
                bw.flush();
            }
        } catch (IOException e) {
            System.err.println("["+new Date()+"]"+" ("+e.getClass().getSimpleName()+") "+e.getMessage());
        }
    }
}