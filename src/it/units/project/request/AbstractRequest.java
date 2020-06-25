package it.units.project.request;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.ComputationException;
import it.units.project.exception.MalformedInputRequest;

public abstract class AbstractRequest {
    protected final String request;
    protected double[] stats;
    private double time;

    public abstract String process() throws CommandException, MalformedInputRequest, BadDomainDefinition, ComputationException;

    public AbstractRequest(String input){
        request = input;
        time = System.nanoTime();
    }

    public AbstractRequest(String input, double[] stats){
        request = input;
        this.stats = stats;
        time = System.nanoTime();
    }

    public double closeRequest(){
        time = (System.nanoTime() - time) / 1_000_000_000.0;
        return time;
    }

    public double getTime(){
        return time;
    }

    @Override
    public String toString() {
        return request;
    }
}
