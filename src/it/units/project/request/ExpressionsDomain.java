package it.units.project.request;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


public class ExpressionsDomain {
    public Map<String, double[]> variablesDomain;
    private String valuesKind;

    public ExpressionsDomain(String valuesKind, Map<String, double[]> variablesDomain) {
        this.valuesKind = valuesKind;
        this.variablesDomain = variablesDomain;
    }

    public List<List<Double>> expressionDomainProcessing() throws BadDomainDefinition, CommandException {
        List<List<Double>> expressionsDomain;
        switch (valuesKind) {
            case "GRID":
                expressionsDomain = makeGrid();
                break;

            case "LIST":
                expressionsDomain = makeList();
                break;

            default:
                throw new CommandException("Protocol's values kind format not met: " + valuesKind);
        }
        return expressionsDomain;
    }

    private List<List<Double>> makeGrid() {
        List<List<Double>> tuples = new ArrayList<>();
        Set<String> variablesNames = variablesDomain.keySet();
        for (String name : variablesNames) {
            double[] values = variablesDomain.get(name);
            if(values != null){
                List<Double> tuple = DoubleStream.of(values).boxed().collect(Collectors.toList());
                tuples.add(tuple);
            }
        }
        return cartesianProduct(tuples);
    }

    private List<List<Double>> makeList() throws BadDomainDefinition {
        List<List<Double>> tuples = new ArrayList<>();
        Set<String> variablesNames = variablesDomain.keySet();
        int domainSize = haveSameSize();
        if (domainSize == -1) {
            throw new BadDomainDefinition("Variables don't have the same domain size");
        }
        for(int j=0; j<domainSize; j++){
            List<Double> tuple = new ArrayList<>();
            for (String name : variablesNames) {
                double[] values = variablesDomain.get(name);
                if(values != null){
                    tuple.add(values[j]);
                }
            }
            tuples.add(tuple);
        }
        return tuples;
    }

    private <T> List<List<T>> cartesianProduct(List<List<T>> lists) {
        List<List<T>> resultLists = new ArrayList<List<T>>();
        if (lists.size() == 0) {
            resultLists.add(new ArrayList<T>());
            return resultLists;
        } else {
            List<T> firstList = lists.get(0);
            List<List<T>> remainingLists = cartesianProduct(lists.subList(1, lists.size()));
            for (T condition : firstList) {
                for (List<T> remainingList : remainingLists) {
                    ArrayList<T> resultList = new ArrayList<T>();
                    resultList.add(condition);
                    resultList.addAll(remainingList);
                    resultLists.add(resultList);
                }
            }
        }
        return resultLists;
    }

    private int haveSameSize() {
        Set<String> variablesNames = variablesDomain.keySet();
        int lastLength = -1;
        for (String name : variablesNames) {
            double[] values = variablesDomain.get(name);
            if(values != null){
                if ((lastLength != values.length) && (lastLength != -1)){
                    return -1;
                }
                lastLength = values.length;
            }
        }
        return lastLength;
    }
}
