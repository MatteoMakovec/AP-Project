package it.units.project.server;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.ComputationException;
import it.units.project.exception.MalformedInputRequest;
import it.units.project.request.ComputationRequest;
import it.units.project.request.AbstractRequest;
import it.units.project.request.StatRequest;
import it.units.project.response.ErrorResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server implements CommandProcessor {
    private final int port;
    private final String quitCommand;
    private double[] stats;

    public Server(int port, String quitCommand) {
        this.port = port;
        this.quitCommand = quitCommand;
        stats = new double[3];
    }

    public void start () throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                    System.err.println("["+new Date()+"] New connection from client");
                    ClientHandler clientHandler = new ClientHandler(socket, quitCommand, this);
                    clientHandler.start();
                } catch (IOException e) {
                    System.err.printf("["+new Date()+"] Cannot accept connection due to %s", e);
                }
            }
        }
    }

    public String process(String input) {
        AbstractRequest request;
        int index = input.indexOf(";");
        String response;
        double startTime = System.nanoTime();

        // TODO: gestire l'accesso concorrente alla risorsa condivisa double[] stats

        if (index == -1){
            request = new StatRequest(input, stats);
            try {
                response = request.process(request);
                double totalTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
                response = response.replace("totalTime", String.format("%.5f", totalTime));
                stats[0] += 1;
                stats[1] += totalTime;
                if (totalTime > stats[2]){
                    stats[2] = totalTime;
                }
            } catch (CommandException | MalformedInputRequest | BadDomainDefinition | ComputationException e){
                response = new ErrorResponse("("+e.getClass().getSimpleName()+") "+e.getMessage()).buildingResponse();
            }
        }
        else{       // TODO: Da implementare l'Executor per far partire massimo n computazioni, dove n è il numero di core disponibili alla JVM
            request = new ComputationRequest(input);
            try {
                response = request.process(request);
                double totalTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
                response = response.replace("totalTime", String.format("%.5f", totalTime));
                stats[0] += 1;
                stats[1] += totalTime;
                if (totalTime > stats[2]){
                    stats[2] = totalTime;
                }
            } catch (CommandException | MalformedInputRequest | BadDomainDefinition | ComputationException e){
                response = new ErrorResponse("("+e.getClass().getSimpleName()+") "+e.getMessage()).buildingResponse();
            }
        }

        return response;
    }

    public String getQuitCommand() {
        return quitCommand;
    }
}
