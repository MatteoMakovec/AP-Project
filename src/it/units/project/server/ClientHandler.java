package it.units.project.server;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.MalformedInputRequest;
import it.units.project.request.ComputationRequest;
import it.units.project.request.Request;
import it.units.project.request.StatRequest;
import it.units.project.response.ErrorResponse;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class ClientHandler extends Thread {
    private final Socket socket;
    private final Server server;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (socket;
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ) {
            Collection<Request> requests = new ArrayList<>();
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    System.err.println("["+new Date()+"] Client abruptly closed connection");
                    break;
                }
                if (line.equals(server.getQuitCommand())) {
                    socket.close();
                    break;
                }
                Request request = inputProcessing(line, requests);
                String response;
                try {
                    response = request.process(request);
                } catch (CommandException|MalformedInputRequest|BadDomainDefinition e){
                    response = new ErrorResponse(request, "("+e.getClass().getSimpleName()+") "+e.getMessage()).buildingResponse();
                }
                bw.write(response + System.lineSeparator());
                bw.flush();
                requests.add(request);
            }
        } catch (IOException e) {
            System.err.println("["+new Date()+"]"+" ("+e.getClass().getSimpleName()+") "+e.getMessage());
        }
    }

    public Request inputProcessing(String request, Collection<Request> requests){
        int index = request.indexOf(";");

        if (index == -1){
            return new StatRequest(request, System.nanoTime(), requests);
        }
        else{
            return new ComputationRequest(request, System.nanoTime());
        }
    }
}