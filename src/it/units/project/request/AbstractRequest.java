package it.units.project.request;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.ComputationException;
import it.units.project.exception.MalformedInputRequest;
import java.util.Collection;

public abstract class AbstractRequest {
    protected final String input;
    protected double processTime;
    protected boolean state;
    protected Collection<AbstractRequest> requests;

    public abstract String process(AbstractRequest r) throws CommandException, MalformedInputRequest, BadDomainDefinition, ComputationException;

    public AbstractRequest(String regex, double time){
        input = regex;
        processTime = time;
    }

    public AbstractRequest(String regex, double time, Collection<AbstractRequest> req){
        input = regex;
        processTime = time;
        requests = req;
    }

    public double closeRequest(boolean state) {
        this.state = state;
        processTime = (System.nanoTime() - processTime)/ 1_000_000_000.0;

        return processTime;
    }

    @Override
    public String toString() {
        return input;
    }
}
