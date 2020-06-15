package it.units.project.request;

import java.util.StringTokenizer;

public class VariableValuesFunction {
    private String toParse;

    public VariableValuesFunction (String toParse){
        this.toParse = toParse;
    }

    public void functionVariableGeneration(){
        String[] variables = variablesTokenizer();
        for (String s : variables){
            char separator = ':';
            int indexSeparator = s.indexOf(separator);
            String variableName = s.substring(0, indexSeparator);
            s = s.substring(separator+1);
            indexSeparator = s.indexOf(separator);
            double lower = Double.parseDouble(s.substring(0, indexSeparator));
            s = s.substring(separator+1);
            indexSeparator = s.indexOf(separator);
            double step = Double.parseDouble(s.substring(0, indexSeparator));
            s = s.substring(separator+1);
            indexSeparator = s.indexOf(separator);
            double upper = Double.parseDouble(s.substring(0, indexSeparator));

            if(step <= 0){
                // errore: step non può essere negativo --> la request fallisce
            }
            if(upper < upper){
                // errore: upper non può essere inferiore al lower --> la request fallisce
            }

            System.out.println("variableName: " + variableName + "lower: " + lower + "step: " + step + "upper: " + upper);
        }
    }

    private String[] variablesTokenizer (){
        StringTokenizer variablesToken = new StringTokenizer(toParse, ",");
        String[] variables = new String[variablesToken.countTokens()];
        int i = 0;

        while (variablesToken.hasMoreTokens())
        {
            System.out.println(variablesToken.nextToken());
            variables[i] = (variablesToken.nextToken());
            i++;
        }

        return variables;
    }
}
