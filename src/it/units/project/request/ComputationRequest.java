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
    public ComputationRequest(String input){
        super(input);
    }

    public String process() throws MalformedInputRequest, BadDomainDefinition, CommandException, ComputationException, IllegalArgumentException {
        StringTokenizer computationRequest = new StringTokenizer(request, "_;");
        int totalTokens = computationRequest.countTokens();
        if(totalTokens < 4){
            System.err.println("["+new Date()+"] Some computation specifications are missing");
            throw new MalformedInputRequest("Some computation specifications are missing");
        }
        String[] computationSpecifications = new String[totalTokens];
        int i = 0;
        while (computationRequest.hasMoreTokens()) {
            computationSpecifications[i] = computationRequest.nextToken();
            i++;
        }
        List<String> expressions = new ArrayList<>();
        for (int j=3; j<computationSpecifications.length; j++){
            expressions.add(computationSpecifications[j]);
        }

        ExpressionsDomain expressionsDomain = new ExpressionsDomain(computationSpecifications[1], new VariablesDomain(computationSpecifications[2]).domainGenerator());
        Computation c = new Computation(computationSpecifications[0], expressions, expressionsDomain);
        return new SuccessfulResponse(c.compute()).buildingResponse();
    }
}
