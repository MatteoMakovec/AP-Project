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
    private final CommandProcessor processor;
    private final String quitCommand;

    public ClientHandler(Socket socket, String quitCommand, CommandProcessor server) {
        this.socket = socket;
        this.processor = server;
        this.quitCommand = quitCommand;
    }

    public void run() {
        try (socket;
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    System.err.println("["+new Date()+"] Client abruptly closed connection");
                    break;
                }
                if (line.equals(quitCommand)) {
                    socket.close();
                    System.out.println("["+new Date()+"] Client disconnected");
                    break;
                }
                bufferedWriter.write(processor.process(line) + System.lineSeparator());
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            System.err.println("["+new Date()+"]"+" ("+e.getClass().getSimpleName()+") "+ "Client abruptly closed connection");
        }
    }
}