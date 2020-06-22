package it.units.project.request;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.ComputationException;
import it.units.project.exception.MalformedInputRequest;
import it.units.project.response.SuccessfulResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


public class ComputationRequest extends AbstractRequest {
    public ComputationRequest(String regex, double time){
        super(regex, time);
    }

    public String process(AbstractRequest r) throws MalformedInputRequest, BadDomainDefinition, CommandException, ComputationException {
        return new SuccessfulResponse(r, inputProcessing(r.request)).buildingResponse();
    }

    private String inputProcessing(String input) throws MalformedInputRequest, BadDomainDefinition, CommandException, ComputationException {
        StringTokenizer computationRequest = new StringTokenizer(input, "_;");
        int totalTokens = computationRequest.countTokens();
        if(totalTokens < 4){
            // TODO: errore, devono esserci 4 tokens
            System.err.println("["+new Date()+"] Some computation specifications are missing");
            throw new MalformedInputRequest("Some computation specifications are missing");
        }
        String[] formatRequest = new String[totalTokens];
        int i = 0;
        while (computationRequest.hasMoreTokens()) {
            formatRequest[i] = computationRequest.nextToken();
            i++;
        }

        List<String> expressions = new ArrayList<>();
        for (int l=3; l<formatRequest.length; l++){
            expressions.add(formatRequest[l]);
        }
        ExpressionsDomain expressionsDomain = new ExpressionsDomain(formatRequest[1], new VariablesDomain(formatRequest[2]).domainGenerator());
        Computation c = new Computation(formatRequest[0], expressions, expressionsDomain);
        return c.compute();
    }
}
