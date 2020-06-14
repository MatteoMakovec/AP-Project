package it.units.project.request;

import java.util.Collection;

public abstract class Request {
    protected final String input;
    protected long processTime;
    protected boolean state;

    public abstract String process(Collection<Request> req);

    public Request(String regex, long time){
        input = regex;
        processTime = time;
    }

    public void closeRequest(boolean state){                // Da rimettere protected
        processTime = System.nanoTime() - processTime;
        this.state = state;
    }

    @Override
    public String toString() {
        return input;
    }
}
