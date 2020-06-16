package it.units.project.request;

import java.util.Collection;
import java.util.StringTokenizer;

public class ComputationRequest extends Request{
    public ComputationRequest(String regex, long time){
        super(regex, time);
    }

    public String process(Collection<Request> requests, Request r){     // TODO: Non servirebbe passare la Collection, ma devo per via della classe astratta
        return inputProcessing(r.input);
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
        VariablesDomain vd = new VariablesDomain(formatRequest[2]);
        double[] values = vd.domainGenerator().get("x1");

        for (int k=0; k<values.length; k++ ){
            result += values[k] + ", ";
        }

        return result;
    }
}
