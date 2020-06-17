package it.units.project.request;

import it.units.project.response.ErrorResponse;
import it.units.project.response.SuccessfulResponse;
import java.util.Collection;

public class StatRequest extends Request{
    public StatRequest(String regex, long time, Collection<Request> requests){
        super(regex, time, requests);
    }

    public String process(Request r){
        String result = "";

        switch (input){
            case "STAT_REQS":
                int numberOfSuccess = 0;
                for(Request request : requests){
                    if(request.state == true){
                        numberOfSuccess++;
                    }
                }
                result = new SuccessfulResponse(r, Integer.toString(numberOfSuccess)).buildingResponse();
                break;

            case "STAT_AVG_TIME":
                double total_time = 0;
                for(Request request : requests){
                    total_time += request.processTime;
                }
                double average_time = total_time / requests.size();
                result = new SuccessfulResponse(r, Double.toString(average_time)).buildingResponse();
                break;

            case "STAT_MAX_TIME":
                double max_time = 0;
                for(Request request : requests){
                    if(request.processTime > max_time){
                        max_time = request.processTime;
                    }
                }
                result = new SuccessfulResponse(r, Double.toString(max_time)).buildingResponse();
                break;

            default:
                result = new ErrorResponse(r, "(Command exception) Command not found").buildingResponse();
                break;
        }
        return result;
    }
}
