package it.units.project.request;

import it.units.project.response.SuccessfulResponse;

import java.util.*;

public class ComputationRequest extends Request{
    public ComputationRequest(String regex, long time){
        super(regex, time);
    }

    public String process(Request r){
        return new SuccessfulResponse(r, inputProcessing(r.input)).buildingResponse();
    }

    private String inputProcessing(String input){
        String result = "";

        StringTokenizer computationRequest = new StringTokenizer(input, "_;");
        int totalTokens = computationRequest.countTokens();
        if(totalTokens < 4){
            System.err.println("ERROR");
            // TODO: errore, devono esserci 4 tokens
        }
        String[] formatRequest = new String[totalTokens];
        int i = 0;
        while (computationRequest.hasMoreTokens()) {
            formatRequest[i] = computationRequest.nextToken();
            i++;
        }

        // TODO: Computazione del valore voluto
        List<String> expressions = new ArrayList<>();
        for (int l=3; l<formatRequest.length; l++){
            expressions.add(formatRequest[l]);
        }
        ExpressionsDomain expressionsDomain = new ExpressionsDomain(formatRequest[1], new VariablesDomain(formatRequest[2]).domainGenerator());
        Computation c = new Computation(formatRequest[0], expressions, expressionsDomain);
        return c.compute();
    }
}
