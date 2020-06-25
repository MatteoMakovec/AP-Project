package it.units.project.server;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.ComputationException;
import it.units.project.exception.MalformedInputRequest;
import it.units.project.request.AbstractRequest;
import it.units.project.request.ComputationRequest;
import it.units.project.request.StatRequest;
import it.units.project.response.ErrorResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ProcessingServer extends Server{
    private double[] stats;
    public static int DECIMAL_PRECISION = 6;

    public ProcessingServer(int port, String quitCommand) {
        super(port, quitCommand);
        stats = new double[3];
    }

    @Override
    public String process(String input) {
        int separatorIndex = input.indexOf(";");
        AbstractRequest request;
        String response;

        double[] statsRetrieved;
        synchronized (stats) {
            statsRetrieved = stats;
        }

        if (separatorIndex == -1){
            request = new StatRequest(input, statsRetrieved);
            response = requestProcessing(request);
        }
        else{
            request = new ComputationRequest(input);
            Future<String> future = executorService.submit(() -> requestProcessing(request));
            try{
                response = future.get();
            } catch (InterruptedException | ExecutionException e){
                response = new ErrorResponse("("+e.getClass().getSimpleName()+") "+e.getMessage()).buildingResponse();
            }
        }
        return response;
    }

    private String requestProcessing(AbstractRequest request){
        String response;

        try {
            response = request.process();
            synchronized (stats) {
                stats[0] += 1;
                stats[1] += request.getTime();
                if (request.getTime() > stats[2]){
                    stats[2] = request.getTime();
                }
            }
        } catch (CommandException | MalformedInputRequest | BadDomainDefinition | ComputationException | IllegalArgumentException e){
            response = new ErrorResponse("("+e.getClass().getSimpleName()+") "+e.getMessage()).buildingResponse();
        }
        return response;
    }
}
