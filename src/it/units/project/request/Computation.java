package it.units.project.request;

import it.units.project.expression.*;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Computation {
    private final int CHILDREN_LIST_SIZE = 2;
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
                result = String.valueOf(computation(expressions.get(0).toString()));
                break;

            case "MAX":
                result = String.valueOf(computation(expressions.get(0).toString()));
                break;

            case "AVG":
                result = String.valueOf(computation(expressions.get(0).toString()));
                break;

            case "COUNT":
                result = String.valueOf(computation(expressions.get(0).toString()));
                break;

            default:
                System.err.println("Protocollo non rispettato");
            // TODO: ERRORE, protocollo non rispettato
        }
        return result;
    }

    private double computation(String stringToProcess){
        double value = 0;
        Node n = new Parser(stringToProcess).parse();
        if(n instanceof Operator){
            Object[] s = n.getChildren().toArray();
            Function<double[], Double> function = ((Operator) n).getType().getFunction();
            double[] values = new double[CHILDREN_LIST_SIZE];
            for (int l=0; l<CHILDREN_LIST_SIZE; l++){
                values[l] = computation(n.getChildren().get(l).toString());
            }
            return function.apply(values);
        }

        if(n instanceof Variable){
            switch (((Variable) n).getName()){
                case "x0":
                    value = 1;
                    break;

                case "x1":
                    value = 2;
                    break;

                default:
                    break;
            }
            return value;
        }

        if(n instanceof Constant){
            value = ((Constant) n).getValue();
            return value;
        }

        return value;
    }
}
