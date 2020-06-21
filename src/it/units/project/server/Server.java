package it.units.project.server;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.ComputationException;
import it.units.project.exception.MalformedInputRequest;
import it.units.project.request.ComputationRequest;
import it.units.project.request.Request;
import it.units.project.request.StatRequest;
import it.units.project.response.ErrorResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Server implements CommandProcessor{
    private final int port;
    private final String quitCommand;
    private Collection<Request> requests;   /**
                                            *       TODO: da modificare sia la collection che la gestione delle STAT REQUEST
                                            *       (occhio alla gestione dell'accesso alla risorsa comune col multithreading)
                                            */

    public Server(int port, String quitCommand) {
        this.port = port;
        this.quitCommand = quitCommand;
        requests = new ArrayList<>();
    }

    public void start () throws IOException{
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    System.err.println("["+new Date()+"] New connection from client");
                    ClientHandler clientHandler = new ClientHandler(socket, this);
                    clientHandler.start();
                } catch (IOException e) {
                    System.err.printf("["+new Date()+"] Cannot accept connection due to %s", e);
                }
            }
        }
    }

    public String process(String input) {
        Request request = inputProcessing(input);
        String response;
        try {
            response = request.process(request);
        } catch (CommandException | MalformedInputRequest | BadDomainDefinition | ComputationException e){
            response = new ErrorResponse(request, "("+e.getClass().getSimpleName()+") "+e.getMessage()).buildingResponse();
        }
        requests.add(request);
        return response;
    }

    public String getQuitCommand() {
        return quitCommand;
    }

    private Request inputProcessing(String request){
        int index = request.indexOf(";");

        if (index == -1){
            return new StatRequest(request, System.nanoTime(), requests);
        }
        else{
            return new ComputationRequest(request, System.nanoTime());
        }
    }
}
