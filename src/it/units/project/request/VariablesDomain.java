package it.units.project.request;

import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;

public class VariablesDomain {
    private String[] variablesValues;

    public VariablesDomain(String values){
        variablesValues = variableValuesProcessing(values);
    }

    public Map<String, double[]> domainGenerator(){
        Map<String, double[]> domain = new HashMap<>();
        for (int i = 0; i < variablesValues.length ; i+=4){
            int domainSize = (int) (Double.parseDouble(variablesValues[i+3]) - Double.parseDouble(variablesValues[i+1]) / Double.parseDouble(variablesValues[i+2]));
            double[] values = new double[domainSize];
            for (int j = 0; j < domainSize; j++){
                values[j] = Double.parseDouble(variablesValues[i+1]) + j * Double.parseDouble(variablesValues[i+2]);
            }
            domain.put(variablesValues[i], values);
        }

        return domain;
    }
    private String[] variableValuesProcessing(String input){
        StringTokenizer variables = new StringTokenizer(input, ",");
        int totalTokens = variables.countTokens();
        String[] variablesDefinition = {};

        if(totalTokens > 0){
            variablesDefinition = new String[totalTokens*4];
            int i = 0;
            while (variables.hasMoreTokens()) {
                String definition = variables.nextToken();
                StringTokenizer items = new StringTokenizer(definition, ":");
                if(items.countTokens() != 4){
                    System.err.println("ERROR");
                    // TODO: errore, devono esserci 4 tokens
                }
                int j = 0;
                while (items.hasMoreTokens()) {
                    variablesDefinition[i+j] = items.nextToken();
                    j++;
                }
                i++;
            }
        }

        return variablesDefinition;
    }
}
