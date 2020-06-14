package it.units.project.request;

public abstract class Request {
    protected final String request;
    protected long processTime;

    public abstract String process();

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

    public long getProcessTime(){
        return processTime;
    }
}
