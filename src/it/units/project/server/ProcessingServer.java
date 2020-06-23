package it.units.project.server;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.ComputationException;
import it.units.project.exception.MalformedInputRequest;
import it.units.project.request.AbstractRequest;
import it.units.project.request.ComputationRequest;
import it.units.project.request.StatRequest;
import it.units.project.response.ErrorResponse;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProcessingServer extends Server{
    public static ExecutorService executorService;

    public ProcessingServer(int port, String quitCommand) {
        super(port, quitCommand);
        executorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public String process(String input) {
        AbstractRequest request;
        int index = input.indexOf(";");
        String response;
        double startTime = System.nanoTime();

        double[] provvStats;
        synchronized (stats) {
            provvStats = stats;
        }

        if (index == -1){
            request = new StatRequest(input, provvStats);
            try {
                response = request.process();
                double totalTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
                response = response.replace("totalTime", String.format("%.5f", totalTime));
                provvStats[0] += 1;
                provvStats[1] += totalTime;
                if (totalTime > provvStats[2]){
                    provvStats[2] = totalTime;
                }
                synchronized (stats) {
                    stats = provvStats;
                }
            } catch (CommandException | MalformedInputRequest | BadDomainDefinition | ComputationException | IllegalArgumentException e){
                response = new ErrorResponse("("+e.getClass().getSimpleName()+") "+e.getMessage()).buildingResponse();
            }
        }
        else{
            request = new ComputationRequest(input);
            Future<String> future = executorService.submit(() -> {
                String provvResponse = "";
                Thread.sleep(5000);
                try {
                    provvResponse = request.process();
                    double totalTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
                    provvResponse = provvResponse.replace("totalTime", String.format("%.5f", totalTime));
                    stats[0] += 1;
                    stats[1] += totalTime;
                    if (totalTime > stats[2]) {
                        stats[2] = totalTime;
                    }
                    synchronized (stats) {
                        stats = provvStats;
                    }
                } catch (CommandException | MalformedInputRequest | BadDomainDefinition | ComputationException | IllegalArgumentException e) {
                    provvResponse = new ErrorResponse("(" + e.getClass().getSimpleName() + ") " + e.getMessage()).buildingResponse();
                }
                finally {
                    return provvResponse;
                }
            });
            try{
                response = future.get();
            } catch (InterruptedException | ExecutionException e){
                System.err.printf("["+new Date()+"] Cannot accept connection due to %s", e);
                response = new ErrorResponse("("+e.getClass().getSimpleName()+") "+e.getMessage()).buildingResponse();
            }
        }

        return response;
    }
}
