package it.units.project.request;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.ComputationException;
import it.units.project.exception.MalformedInputRequest;

public abstract class AbstractRequest {
    protected final String request;
    protected double[] stats;

    public abstract String process() throws CommandException, MalformedInputRequest, BadDomainDefinition, ComputationException;

    public AbstractRequest(String input){
        request = input;
    }

    public AbstractRequest(String input, double[] stats){
        request = input;
        this.stats = stats;
    }

    @Override
    public String toString() {
        return request;
    }
}
