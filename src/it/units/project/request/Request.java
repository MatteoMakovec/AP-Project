package it.units.project.request;

import java.util.Collection;

public abstract class Request {
    protected final String request;
    protected long processTime;
    protected boolean state;

    public abstract String process(Collection<Request> req);

    public Request(String regex, long time){
        request = regex;
        processTime = time;
    }

    protected void closeRequest(boolean state){
        processTime = System.nanoTime() - processTime;
        this.state = state;
    }

    @Override
    public String toString() {
        return request;
    }
}
