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
import java.util.concurrent.Future;

public class ProcessingServer extends Server{
    private double[] stats;
    private double[] statsRetrieved;

    public ProcessingServer(int port, String quitCommand) {
        super(port, quitCommand);
        stats = new double[3];
    }

    @Override
    public String process(String input) {
        int separatorIndex = input.indexOf(";");
        AbstractRequest request;
        String response;

        synchronized (stats) {
            statsRetrieved = stats;
        }

        if (separatorIndex == -1){
            request = new StatRequest(input, statsRetrieved);
            response = requestProcessing(request);
        }
        else{
            request = new ComputationRequest(input);
            Future<String> future = executorService.submit(() -> {
                String responseBuilt = requestProcessing(request);
                return responseBuilt;
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

    private String requestProcessing(AbstractRequest request){
        String response;
        double startTime = System.nanoTime();

        try {
            response = request.process();
            double totalTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
            response = response.replace("totalTime", String.format("%.6f", totalTime));
            statsRetrieved[0] += 1;
            statsRetrieved[1] += totalTime;
            if (totalTime > statsRetrieved[2]){
                statsRetrieved[2] = totalTime;
            }
            synchronized (stats) {
                stats = statsRetrieved;
            }
        } catch (CommandException | MalformedInputRequest | BadDomainDefinition | ComputationException | IllegalArgumentException e){
            response = new ErrorResponse("("+e.getClass().getSimpleName()+") "+e.getMessage()).buildingResponse();
        }
        return response;
    }
}
