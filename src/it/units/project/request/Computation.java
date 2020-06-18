package it.units.project.request;

import java.util.Collections;
import java.util.List;

public class Computation {
    private List<String> expressions;
    private ExpressionsDomain expressionsDomain;
    private String computationKind;

    public Computation(String computationKind, List<String> expressions, ExpressionsDomain expressionsDomain){
        this.expressions = expressions;
        this.expressionsDomain = expressionsDomain;
        this.computationKind = computationKind;
    }

    public String compute(){
        String result = "";
        switch (computationKind) {
            case "MIN":

                break;

            case "MAX":

                break;

            case "AVG":

                break;

            case "COUNT":

                break;

            default:
                System.err.println("Protocollo non rispettato");
            // TODO: ERRORE, protocollo non rispettato
        }
        return result;
    }
}
