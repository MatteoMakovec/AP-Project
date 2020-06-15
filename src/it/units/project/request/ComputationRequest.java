package it.units.project.request;

import java.util.Collection;

public class ComputationRequest extends Request{
    public ComputationRequest(String regex, long time){
        super(regex, time);
    }

    public String process(Collection<Request> requests, Request r){     // Non servirebbe passare la Collection, ma devo per via della classe astratta
        String result = inputProcessing(r.input);

        return result;
    }

    private String inputProcessing(String input){
        char computationSeparator = '_';
        char separator = ';';
        String workingString = input;

        String computationKind;
        String valuesKind;
        String variableValues;
        String expression;

        int indexComputationSeparator = workingString.indexOf(computationSeparator);
        computationKind = workingString.substring(0, indexComputationSeparator);
        int indexSeparator = workingString.indexOf(separator);
        valuesKind = workingString.substring(indexComputationSeparator+1, indexSeparator);
        workingString = workingString.substring(indexSeparator+1);
        indexSeparator = workingString.indexOf(separator);
        variableValues = workingString.substring(0, indexSeparator);
        expression = workingString.substring(indexSeparator+1);

        closeRequest(true);
        String result = "Computation kind: " + computationKind + "\nValues kind: " + valuesKind + "\nVariable values kind: " + variableValues + "\nExpression: " + expression;
        return result;
    }
}
