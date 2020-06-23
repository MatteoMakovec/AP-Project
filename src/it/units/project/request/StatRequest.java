package it.units.project.request;

import it.units.project.exception.CommandException;
import it.units.project.response.SuccessfulResponse;

import java.util.Date;

public class StatRequest extends AbstractRequest {
    public StatRequest(String regex, double[] stats){
        super(regex, stats);
    }

    public String process() throws CommandException{
        String result;

        switch (request){
            case "STAT_REQS":
                result = new SuccessfulResponse(Double.toString(stats[0])).buildingResponse();
                break;

            case "STAT_AVG_TIME":
                result = new SuccessfulResponse(String.format("%.6f", (stats[1] / stats[0]))).buildingResponse();
                break;

            case "STAT_MAX_TIME":
                result = new SuccessfulResponse(String.format("%.6f", stats[2])).buildingResponse();
                break;

            default:
                System.err.println("["+new Date()+"] Command not in protocol specification");
                throw new CommandException("Command not in protocol specification");
        }
        return result;
    }
}
