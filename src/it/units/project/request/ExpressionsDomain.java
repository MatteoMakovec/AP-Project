package it.units.project.request;

import java.util.*;

public class ExpressionsDomain {
    private Map<String, double[]> variablesDomain;
    private String valuesKind;

    public ExpressionsDomain(String valuesKind, Map<String, double[]> variablesDomain) {
        this.valuesKind = valuesKind;
        this.variablesDomain = variablesDomain;
    }

    private List<List<Double>> makeGrid() {
        List<List<Double>> tuples = new ArrayList<>();
        Set<String> variablesNames = variablesDomain.keySet();
        for (String name : variablesNames) {
            double[] d = variablesDomain.get(name);
            List<Double> nupla = new ArrayList<>();
            for (double dd : d) {
                nupla.add(dd);
            }
            tuples.add(nupla);
        }
        return cartesianProduct(tuples);
    }

    private List<List<Double>> makeList() {
        List<List<Double>> tuples = new ArrayList<>();
        Set<String> variablesNames = variablesDomain.keySet();
        int domainSize = haveSameSize();
        if (domainSize == -1) {
            System.err.println("ERRORE: i domini delle due variabili non hanno la stessa grandezza");
            return Collections.emptyList();
            // TODO: ERRORE, i domini delle due variabili non hanno la stessa grandezza
        }
        for(int j=0; j<domainSize; j++){
            List<Double> nupla = new ArrayList<>();
            for (String name : variablesNames) {
                double[] d = variablesDomain.get(name);
                nupla.add(d[j]);
            }
            tuples.add(nupla);
        }
        return tuples;
    }

    public List<List<Double>> expressionDomainProcessing() {
        List<List<Double>> expressionsDomain;
        switch (valuesKind.toUpperCase()) {
            case "GRID":
                expressionsDomain = makeGrid();
                break;

            case "LIST":
                expressionsDomain = makeList();
                break;

            default:
                System.err.println("Protocollo non rispettato");
                expressionsDomain = Collections.emptyList();
                return Collections.emptyList();
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

    private int haveSameSize() {
        Set<String> variablesNames = variablesDomain.keySet();
        int lastLength = -1;
        for (String name : variablesNames) {
            double[] d = variablesDomain.get(name);
            if ((lastLength != d.length) && (lastLength != -1)){
                return -1;
            }
            lastLength = d.length;
        }
        return lastLength;
    }
}
