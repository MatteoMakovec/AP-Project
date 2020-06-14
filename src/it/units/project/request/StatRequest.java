package it.units.project.request;

public class StatRequest extends Request{
    public StatRequest(String regex, long time){
        super(regex, time);
    }

    public String process(){
        switch (request){
            case "STAT_REQS":
                break;

            case "STAT_AVG_TIME":
                break;

            case "STAT_MAX_TIME":
                break;
        }

        closeRequest();
        return "OK";
    }
}
