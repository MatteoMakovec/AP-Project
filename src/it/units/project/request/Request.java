package it.units.project.request;

import it.units.project.exception.BadDomainDefinition;
import it.units.project.exception.CommandException;
import it.units.project.exception.ComputationException;
import it.units.project.exception.MalformedInputRequest;
import java.util.Collection;

public abstract class Request {
    protected final String input;
    protected double processTime;
    protected boolean state;
    protected Collection<Request> requests;

    public abstract String process(Request r) throws CommandException, MalformedInputRequest, BadDomainDefinition, ComputationException;

    public Request(String regex, double time){
        input = regex;
        processTime = time;
    }

    public Request(String regex, double time, Collection<Request> req){
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
