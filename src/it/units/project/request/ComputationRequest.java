package it.units.project.request;

import java.util.Collection;

public class ComputationRequest extends Request{
    public ComputationRequest(String regex, long time){
        super(regex, time);
    }

    public String process(Collection<Request> requests){
        closeRequest(true);
        return "OK";
    }
}
