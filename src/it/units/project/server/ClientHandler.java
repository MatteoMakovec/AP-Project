package it.units.project.server;

import it.units.project.request.ComputationRequest;
import it.units.project.request.Request;
import it.units.project.request.StatRequest;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;


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
            Collection<Request> requests = new ArrayList<>();
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    System.err.println("Client abruptly closed connection");
                    break;
                }
                if (line.toUpperCase().equals(server.getQuitCommand())) {
                    socket.close();
                    break;
                }
                Request req = inputProcessing(line);
                bw.write(req.process(requests, req) + System.lineSeparator());
                bw.flush();
                requests.add(req);
            }
        } catch (IOException e) {
            System.err.printf("IO error: %s\n", e);
        }
    }

    public Request inputProcessing(String request){
        int index = request.indexOf(";");

        if (index == -1){
            return new StatRequest(request, System.nanoTime());     // TODO: Teniamo in nanosecondi o mettiamo in millisecondi?
        }
        else{
            return new ComputationRequest(request, System.nanoTime());      // TODO: Teniamo in nanosecondi o mettiamo in millisecondi?
        }
    }
}