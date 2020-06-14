package it.units.project.request;

import java.util.function.Function;

public class StatRequest extends Request{
    public StatRequest(String regex, long time){
        super(regex, time);
    }
}
