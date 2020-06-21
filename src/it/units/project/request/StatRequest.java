package it.units.project.request;

import it.units.project.exception.CommandException;
import it.units.project.response.SuccessfulResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;

public class StatRequest extends AbstractRequest {
    public StatRequest(String regex, double time, Collection<AbstractRequest> requests){
        super(regex, time, requests);
    }

    public String process(AbstractRequest r) throws CommandException{
        String result;

        switch (input){
            case "STAT_REQS":
                int numberOfSuccess = 0;
                for(AbstractRequest request : requests){
                    if(request.state == true){
                        numberOfSuccess++;
                    }
                }
                result = new SuccessfulResponse(r, Integer.toString(numberOfSuccess)).buildingResponse();
                break;

            case "STAT_AVG_TIME":
                double total_time = 0;
                for(AbstractRequest request : requests){
                    if(request.state == true){
                        total_time += request.processTime;
                    }
                }
                double average_time;
                if (requests.size() == 0){
                    average_time = 0;
                }
                else{
                    average_time = BigDecimal.valueOf(total_time / requests.size()).setScale(3, RoundingMode.HALF_UP).doubleValue();
                }
                result = new SuccessfulResponse(r, String.format("%.5f", average_time)).buildingResponse();
                break;

            case "STAT_MAX_TIME":
                double max_time = 0;
                for(AbstractRequest request : requests){
                    if((request.state == true)&&(request.processTime > max_time)){
                        max_time = request.processTime;
                    }
                }
                //Double.toString(max_time)
                result = new SuccessfulResponse(r, String.format("%.5f", max_time)).buildingResponse();
                break;

            default:
                // TODO: gestire l'eccezione
                System.err.println("["+new Date()+"] Command not in protocol specification");
                throw new CommandException("Command not in protocol specification");
        }
        return result;
    }
}
