package it.units.project.server;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    protected final Socket socket;
    protected final Server server;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                String line = br.readLine();
                if(line.equals(server.getQuitCommand())) {
                    socket.close();
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