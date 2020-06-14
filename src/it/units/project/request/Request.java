package it.units.project.request;

public abstract class Request {
    private final String request;
    protected long processTime;

    public Request(String regex, long time){
        request = regex;
        processTime = time;
    }

    protected void closeRequest(){
        processTime = System.nanoTime() - processTime;
    }

    @Override
    public String toString() {
        return request;
    }
}
