package it.units.project.request;

import java.util.Collection;

public abstract class Request {
    protected final String input;
    protected double processTime;
    protected boolean state;

    public abstract String process(Collection<Request> req, Request r);

    public Request(String regex, long time){
        input = regex;
        processTime = time;
    }

    public double closeRequest(boolean state){
        processTime = System.nanoTime() - processTime;      // TODO: Teniamo in nanosecondi o mettiamo in millisecondi?
        this.state = state;

        return processTime;
    }

    @Override
    public String toString() {
        return input;
    }
}
