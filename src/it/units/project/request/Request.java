package it.units.project.request;

public class Request {
    private final String request;
    protected long processTime;

    public Request(String regex){
        request = regex;
        processTime = System.nanoTime();
    }

    @Override
    public String toString() {
        return request;
    }
}
