package it.units.project.request;

import it.units.project.exception.BadDomainDefinition;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;

public class VariablesDomain {
    public String[] variablesValues;

    public VariablesDomain(String values) throws BadDomainDefinition {
        variablesValues = variableValuesProcessing(values);
    }

    public Map<String, double[]> domainGenerator(){
        Map<String, double[]> domain = new HashMap<>();

        for (int i = 0; i < variablesValues.length ; i+=4){
            int domainSize = (int) ((Double.parseDouble(variablesValues[i+3]) - Double.parseDouble(variablesValues[i+1])) / Double.parseDouble(variablesValues[i+2]));
            double[] values = new double[domainSize+1];
            for (int j = 0; j <= domainSize; j++){
                values[j] = Double.parseDouble(variablesValues[i+1]) + j * Double.parseDouble(variablesValues[i+2]);
            }
            domain.put(variablesValues[i], values);
        }

        return domain;
    }

    private String[] variableValuesProcessing(String input) throws BadDomainDefinition {
        StringTokenizer variables = new StringTokenizer(input, ",");
        int totalTokens = variables.countTokens();
        String[] variablesDefinition = new String[totalTokens*4];

        if(totalTokens > 0){
            int i = 0;
            while (variables.hasMoreTokens()) {
                StringTokenizer items = new StringTokenizer(variables.nextToken(), ":");
                if(items.countTokens() != 4){
                    // TODO: errore, devono esserci 4 tokens
                    System.err.println("["+new Date()+"] Bad definition of variable's domain");
                    throw new BadDomainDefinition("Bad definition of variable's domain");
                }
                int j = 0;
                while (items.hasMoreTokens()) {
                    variablesDefinition[i+j] = items.nextToken();
                    j++;
                }
                if(Double.parseDouble(variablesDefinition[i+2]) <= 0){
                    // TODO: errore, devono esserci 4 tokens
                    System.err.println("["+new Date()+"] Variable's Xstep mustn't be less or equal zero");
                    throw new BadDomainDefinition("Variable's Xstep mustn't be less or equal zero");
                }
                i+=4;
            }
        }

        return variablesDefinition;
    }
}
