package it.units.project.request;

import java.util.Collection;

public class StatRequest extends Request{
    public StatRequest(String regex, long time){
        super(regex, time);
    }

    public String process(Collection<Request> requests){
        String result = "";

        switch (input){
            case "STAT_REQS":
                int numberOfSuccess = 0;
                for(Request request : requests){
                    if(request.state == true){
                        numberOfSuccess++;
                    }
                }
                result = Integer.toString(numberOfSuccess);
                break;

            case "STAT_AVG_TIME":
                long total_time = 0;
                for(Request request : requests){
                    total_time += request.processTime;
                }
                long average_time = total_time / requests.size();
                result = Long.toString(average_time);
                break;

            case "STAT_MAX_TIME":
                long max_time = 0;
                for(Request request : requests){
                    if(request.processTime > max_time){
                        max_time = request.processTime;
                    }
                }
                result = Long.toString(max_time);
                break;

            default:
                System.err.println("Command not found");
                result = "Command not found";
                break;
        }
        return result;
    }
}
