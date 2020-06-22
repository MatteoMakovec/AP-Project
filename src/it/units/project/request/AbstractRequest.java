package it.units.project.request;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.ComputationException;
import it.units.project.exception.MalformedInputRequest;
import java.util.Collection;

public abstract class AbstractRequest {
    protected final String request;
    protected double processTime;
    protected boolean state;
    protected Collection<AbstractRequest> requests;

    public abstract String process(AbstractRequest r) throws CommandException, MalformedInputRequest, BadDomainDefinition, ComputationException;

    public AbstractRequest(String input, double time){
        request = input;
        processTime = time;
    }

    public AbstractRequest(String input, double time, Collection<AbstractRequest> requests){
        request = input;
        processTime = time;
        this.requests = requests;
    }

    public double closeRequest(boolean state) {
        this.state = state;
        processTime = (System.nanoTime() - processTime)/ 1_000_000_000.0;

        return processTime;
    }

    @Override
    public String toString() {
        return request;
    }
}
