package it.units.project.request;

import java.util.*;

public class ExpressionsDomain {
    private Map<String, double[]> variablesDomain;
    private String valuesKind;
    private List<String> expressions;

    public ExpressionsDomain(String valuesKind, Map<String, double[]> variablesDomain, List<String> expressions){
        this.valuesKind = valuesKind;
        this.variablesDomain = variablesDomain;
        this.expressions = expressions;
    }

    private List<List<Double>> makeGrid(){
        List<List<Double>> tuples = new ArrayList<>();
        Set<String> variablesNames = variablesDomain.keySet();
        for (String name : variablesNames){
            double[] d = variablesDomain.get(name);
            List<Double> nupla = new ArrayList<>();
            for (double dd : d){
                nupla.add(dd);
            }
            tuples.add(nupla);
        }
        return cartesianProduct(tuples);
    }

    private List<List<Double>> makeList(){
        List<List<Double>> tuples = new ArrayList<>();
        /** TODO: se i domini delle variabili non hanno la stessa lunghezza, allora ERRORE.
         *        Altrimenti calcolare la lista
         */

        return tuples;
    }

    public List<List<Double>> expressionDomainProcessing(){
        List<List<Double>> expressionsDomain;
        switch (valuesKind.toUpperCase()){
            case "GRID":
                expressionsDomain = makeGrid();
                break;

            case "LIST":
                expressionsDomain = makeList();
                break;

            default:
                System.err.println("Protocollo non rispettato");
                expressionsDomain = Collections.emptyList();
                // TODO: ERRORE, protocollo non rispettato
        }

        return expressionsDomain;
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
}
