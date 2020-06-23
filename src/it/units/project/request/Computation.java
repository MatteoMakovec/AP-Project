package it.units.project.request;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.ComputationException;
import it.units.project.expression.*;

import java.util.Date;
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

    public String compute() throws BadDomainDefinition, CommandException, ComputationException, IllegalArgumentException {
        String result = "";
        List<List<Double>> expressionDomain = expressionsDomain.expressionDomainProcessing();

        switch (computationKind) {
            case "MIN":
                for (List<Double> tuple : expressionDomain){
                    for (String expression : expressions){
                        double total = valueComputation(expression, tuple);
                        if(result == ""){
                            result = String.valueOf(total);
                        }
                        else if (Double.parseDouble(result) > total){
                            result = String.valueOf(total);
                        }
                    }
                }
                break;

            case "MAX":
                for (List<Double> tuple : expressionDomain){
                    for (String expression : expressions){
                        double total = valueComputation(expression, tuple);
                        if(result.equals("")){
                            result = String.valueOf(total);
                        }
                        else if (Double.parseDouble(result) < total){
                            result = String.valueOf(total);
                        }
                    }
                }
                break;

            case "AVG":
                int divider = 0;
                double sum = 0;
                for (List<Double> tuple : expressionDomain){
                    divider++;
                    sum += valueComputation(expressions.get(0), tuple);
                }
                result = String.valueOf(sum/divider);
                break;

            case "COUNT":
                int domainSize = 0;
                for (List<Double> tuple : expressionDomain){
                    domainSize++;
                }
                result = String.valueOf(domainSize);
                break;

            default:
                System.err.println("["+new Date()+"] Protocol's computation kind format not met");
                throw new CommandException("Protocol's computation kind format not met");
        }
        return String.format("%.6f", Double.parseDouble(result));
    }

    private double valueComputation(String stringToProcess, List<Double> tuple) throws ComputationException, IllegalArgumentException {
        double value = 0;
        Node node = new Parser(stringToProcess).parse();

        if(node instanceof Operator){
            Function<double[], Double> function = ((Operator) node).getType().getFunction();
            double[] values = new double[CHILDREN_LIST_SIZE];
            for (int l=0; l<CHILDREN_LIST_SIZE; l++){
                values[l] = valueComputation(node.getChildren().get(l).toString(), tuple);
            }
            return function.apply(values);
        }
        if(node instanceof Variable){
            Object[] indexes = expressionsDomain.variablesDomain.keySet().toArray();
            boolean control = false;
            for (int i=0; i<indexes.length; i++){
                if(indexes[i].equals(((Variable) node).getName())){
                    value = tuple.get(i);
                    control = true;
                }
            }
            if (control == false){
                throw new ComputationException("Unvalued variable " + ((Variable) node).getName());
            }
            return value;
        }
        if(node instanceof Constant){
            value = ((Constant) node).getValue();
            return value;
        }

        return value;
    }
}
