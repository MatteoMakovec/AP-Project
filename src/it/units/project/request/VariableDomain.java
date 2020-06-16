package it.units.project.request;

import java.util.HashMap;
import java.util.Map;

public class VariableDomain {
    private String variableName;
    private double lower;
    private double step;
    private double upper;

    public VariableDomain(String variableName, double lower, double step, double upper){
        this.variableName = variableName;
        this.lower = lower;
        this.step = step;
        this.upper = upper;
    }

    public Map<String, double[]> domainGenerator(){
        Map<String, double[]> domain = new HashMap<>();
        int domainSize = (int) ((upper-lower)/step);

    }
}
