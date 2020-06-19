package it.units.project.request;

import it.units.project.expression.*;

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
        List<List<Double>> expressionDomain = expressionsDomain.expressionDomainProcessing();

        switch (computationKind) {
            case "MIN":
                for (List<Double> nupla : expressionDomain){
                    for (String expression : expressions){
                        double res = computation(expression, nupla);
                        if(result == ""){
                            result = String.valueOf(res);
                        }
                        else if (Double.parseDouble(result) > res){
                            result = String.valueOf(res);
                        }
                    }
                }
                break;

            case "MAX":
                for (List<Double> nupla : expressionDomain){
                    for (String expression : expressions){
                        double res = computation(expression, nupla);
                        if(result.equals("")){
                            result = String.valueOf(res);
                        }
                        else if (Double.parseDouble(result) < res){
                            result = String.valueOf(res);
                        }
                    }
                }
                break;

            case "AVG":
                int divider = 0;
                double sum = 0;
                for (List<Double> nupla : expressionDomain){
                    divider++;
                    sum += computation(expressions.get(0), nupla);
                }
                result = String.valueOf(sum/divider);
                break;

            case "COUNT":
                int domainSize = 0;
                for (List<Double> nupla : expressionDomain){
                    domainSize++;
                }
                result = String.valueOf(domainSize);
                break;

            default:
                System.err.println("Protocollo non rispettato");
                // TODO: ERRORE, protocollo non rispettato
        }
        return result;
    }

    private double computation(String stringToProcess, List<Double> nupla){
        double value = 0;
        Node n = new Parser(stringToProcess).parse();

        if(n instanceof Operator){
            Function<double[], Double> function = ((Operator) n).getType().getFunction();
            double[] values = new double[CHILDREN_LIST_SIZE];
            for (int l=0; l<CHILDREN_LIST_SIZE; l++){
                values[l] = computation(n.getChildren().get(l).toString(), nupla);
            }
            return function.apply(values);
        }

        if(n instanceof Variable){
            Object[] indexes = expressionsDomain.variablesDomain.keySet().toArray();
            for (int i=0; i<indexes.length; i++){
                if(indexes[i].equals(((Variable) n).getName())){
                    value = nupla.get(i);
                }
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
